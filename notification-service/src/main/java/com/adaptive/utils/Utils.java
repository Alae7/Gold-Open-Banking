package com.adaptive.utils;



import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.dto.Notification_CompteRequestDto;
import com.adaptive.entity.Notification;
import com.adaptive.entity.NotificationCompte;
import com.adaptive.mapper.NotificationMapper;
import com.adaptive.model.CompteResponseDto;
import com.adaptive.model.CustomerResponseDto;
import com.adaptive.model.TransactionResponseDto;
import com.adaptive.openFeinController.CompteFeinClient;
import com.adaptive.openFeinController.CustomerFiegnClient;
import com.adaptive.openFeinController.TransactionFeinClient;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;


@Component
public class Utils {

    @Autowired
    private  JavaMailSender mailSender;

    @Autowired
    private  NotificationMapper notificationMapper;


    @Autowired
    private  CustomerFiegnClient customerFiegnClient;

    @Autowired
    private  CompteFeinClient compteFeinClient;

    @Autowired
    private  TransactionFeinClient transactionFeinClient;

    public String sendMessage(CustomerResponseDto target, CustomerResponseDto source , TransactionResponseDto transactionResponseDto) {

        try {
            // Determine dynamic description based on the transaction type
            String type = transactionResponseDto.getType().toUpperCase();

            String description = switch (type) {
                case "PAIEMENT" -> "This payment has been successfully processed.";
                case "VERSEMENT" -> "Your versement has been recorded successfully.";
                case "VIREMENT" -> "Your virement has been recorded successfully.";
                case "RETRAIT" -> "Your retrait has been recorded successfully.";
                case "REMBOURSEMENT" -> "The refund has been issued successfully.";
                default -> "Your transaction has been processed.";
            };

            boolean isSimpleTransaction = type.equals("REMBOURSEMENT") || type.equals("VERSEMENT");

            String template = """
        <html>
        <head><title>%s Confirmation – %s</title></head>
        <body style="font-family: Arial, sans-serif; color: #333;">
            <h2 style="color: #4CAF50;">%s – %s</h2>
            <p>Dear Client,</p>
            <p>We are pleased to inform you that the following <strong>%s</strong> has been successfully processed.</p>

            <h3>📌 Transaction Details</h3>
            <ul>
                <li><strong>Transaction ID:</strong> %s</li>
                <li><strong>Type:</strong> %s</li>
                <li><strong>Total Amount:</strong> %s MAD</li>
                <li><strong>Date:</strong> %s</li>
                <li><strong>Sender Module:</strong> %s %s</li>
                %s
                <li><strong>Status:</strong> ✅ %s</li>
            </ul>

            <p><em>%s</em></p>
            <p>Warm regards,<br>Your Gold Open-Banking</p>
        </body>
        </html>
        """;

            String receiverInfo = isSimpleTransaction ? "" :
                    String.format("<li><strong>Receiver Module:</strong> %s %s</li>", target.getFirstName(), target.getLastName());

            String emailContent = String.format(template,
                    type, transactionResponseDto.getUuid(),
                    type, transactionResponseDto.getUuid(),
                    type,
                    transactionResponseDto.getUuid(),
                    type,
                    transactionResponseDto.getAmount(),
                    transactionResponseDto.getCreateDateTime(),
                    source.getFirstName(), source.getLastName(),
                    receiverInfo,
                    transactionResponseDto.getStatus(),
                    description
            );

            // Send it to receiver

            sendEmail(target.getEmail(),type,transactionResponseDto.getUuid(),emailContent);

            // Send it to sender

            sendEmail(source.getEmail(),type,transactionResponseDto.getUuid(),emailContent);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to send emails: " + e.getMessage();
        }
        return "✅ Emails sent successfully to both sender and receiver.";
    }

    public Notification createNotification(NotificationRequestDto notificationRequestDto) {
        return notificationMapper.toEntity(notificationRequestDto);
    }

    public NotificationCompte createNotificationCompte(Notification_CompteRequestDto notificationCompteRequestDto) {
        return notificationMapper.toNotificationCompte(notificationCompteRequestDto);
    }

    public CustomerResponseDto getClient(Long rib) {

        String uuid = compteFeinClient.findCustomerUuidByRib(rib);
        return customerFiegnClient.findCustomerById(uuid);

    }

    public TransactionResponseDto getTransaction(String uuid) {

        return transactionFeinClient.findByUuid(uuid);

    }

    public CompteResponseDto getCompte(Long rib) {

        return compteFeinClient.findByRib(rib);

    }

    public String sendAccountStatusMessage(CompteResponseDto compteResponseDto , String isActivated , CustomerResponseDto customerResponseDto) {
        try {

            String messageDescription ;
            boolean status ;

            switch (isActivated) {

                case "ACTIVATION" -> {

                    messageDescription = "Votre compte a été <strong>ACTIVATE</strong> avec succès. Vous pouvez désormais accéder à tous les services bancaires proposés.";
                    status = true;

                }
                case "DEACTIVATION" -> {

                    messageDescription = "Votre compte a été <strong>désactivé</strong>. Pour plus d'informations, veuillez contacter notre service client.";
                    status = false;

                }
                default -> {

                    System.out.println("Unknown event status: " + isActivated);
                    messageDescription = "Unknown event status: " + isActivated;
                    return messageDescription;

                }
            }

            String template = """
            <html>
            <head><title>%s de compte – Gold Open-Banking</title></head>
            <body style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: %s;">%s de votre compte</h2>
                <p>Bonjour %s %s,</p>
                <p>%s</p>

                <h3>📌 Détails du compte</h3>
                <ul>
                    <li><strong>Client:</strong> %s %s</li>
                    <li><strong>Email:</strong> %s</li>
                    <li><strong>N° Comte:</strong> %s</li>
                    <li><strong>Statut du compte:</strong> %s</li>
                    <li><strong>Date:</strong> %s</li>
                </ul>

                <p>Merci pour votre confiance.<br>Gold Open-Banking</p>
            </body>
            </html>
            """;

            String htmlContent = String.format(
                    template,
                    isActivated,
                    status ? "#4CAF50" : "#F44336",
                    status,
                    customerResponseDto.getFirstName(),
                    customerResponseDto.getLastName(),
                    messageDescription,
                    customerResponseDto.getFirstName(),
                    customerResponseDto.getLastName(),
                    customerResponseDto.getEmail(),
                    compteResponseDto.getNumCompte(),
                    status ? "✅ Activé" : "❌ Désactivé",
                    LocalDateTime.now()
            );

            // Préparer et envoyer le mail
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(customerResponseDto.getEmail());
            helper.setSubject("[" + isActivated + "] de votre compte – Gold Open-Banking");
            helper.setText(htmlContent, true);
            helper.setFrom(new InternetAddress("aalae933@gmail.com", "Gold Open-Banking"));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Échec de l'envoi de l'email : " + e.getMessage();
        }

        return "✅ Email envoyé avec succès au client.";
    }


    public String sendAccountCreationMessage(CustomerResponseDto customer ,CompteResponseDto compteResponseDto) {
        try {
            String subject = "Création de votre compte – Gold Open-Banking";
            String description = """
            Nous sommes heureux de vous informer que votre compte a été créé avec succès. 
            Vous pouvez désormais accéder à votre espace personnel et profiter de tous les services offerts par Gold Open-Banking.
            """;

            String template = """
            <html>
            <head><title>Création de compte – Gold Open-Banking</title></head>
            <body style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #2196F3;">Bienvenue chez Gold Open-Banking !</h2>
                <p>Bonjour %s %s,</p>
                <p>%s</p>

                <h3>📌 Informations du compte</h3>
                <ul>
                    <li><strong>Nom complet:</strong> %s %s</li>
                    <li><strong>Email:</strong> %s</li>
                    <li><strong>N° Compte:</strong> %s</li>
                    <li><strong>RIB:</strong> %s</li>
                    <li><strong>Status:</strong> %s</li>
                    <li><strong>Date de création:</strong> %s</li>
                </ul>

                <p>Merci de nous faire confiance.<br>
                L’équipe Gold Open-Banking</p>
            </body>
            </html>
            """;

            String emailContent = String.format(
                    template,
                    customer.getFirstName(),
                    customer.getLastName(),
                    description,
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    compteResponseDto.getNumCompte(),
                    compteResponseDto.getRib(),
                    compteResponseDto.getStatut(),
                    compteResponseDto.getCreateDateTime()
            );

            // Envoi de l'email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(customer.getEmail());
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            helper.setFrom(new InternetAddress("aalae933@gmail.com", "Gold Open-Banking"));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Échec de l'envoi de l'email de création : " + e.getMessage();
        }

        return "✅ Email de création envoyé avec succès au client.";
    }

    public String sendAnomalyMessage(CustomerResponseDto customer ,CompteResponseDto compteResponseDto) {

        try {
            String subject = "Action requise – Anomalie détectée sur votre compte";
            String instruction = """
            Une anomalie a été détectée sur votre compte. 
            Pour des raisons de sécurité, nous vous invitons à vous présenter dans l’une de nos agences avec une pièce d’identité valide afin de régulariser votre situation et activer votre compte.
            """;

            String template = """
            <html>
            <head><title>Anomalie de compte – Action requise</title></head>
            <body style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #F44336;">⚠️ Anomalie détectée – Compte en attente d’activation</h2>
                <p>Bonjour %s %s,</p>
                <p>%s</p>

                <h3>📌 Informations du compte</h3>
                <ul>
                    <li><strong>Nom complet:</strong> %s %s</li>
                    <li><strong>Email:</strong> %s</li>
                    <li><strong>N° Compte:</strong> %s</li>
                    <li><strong>RIB:</strong> %s</li>
                    <li><strong>Statut du compte:</strong> 🚫 En attente (Désactivé)</li>
                </ul>

                <p>Nous restons à votre disposition pour toute question ou assistance complémentaire.</p>
                <p><strong>📍 Merci de vous rendre à l’agence la plus proche dans les plus brefs délais.</strong></p>

                <p>Cordialement,<br>L’équipe Gold Open-Banking</p>
            </body>
            </html>
            """;

            String emailContent = String.format(
                    template,
                    customer.getFirstName(),
                    customer.getLastName(),
                    instruction,
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    compteResponseDto.getNumCompte(),
                    compteResponseDto.getRib(),
                    compteResponseDto.getCreateDateTime()
            );

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(customer.getEmail());
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            helper.setFrom(new InternetAddress("aalae933@gmail.com", "Gold Open-Banking"));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Échec de l'envoi de l'email d'anomalie : " + e.getMessage();
        }

        return "✅ Email d’anomalie envoyé avec succès au client.";
    }

    private void sendEmail(String to, String type, String uuid, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(type + " Confirmation – " + uuid);
        helper.setText(content, true);
        helper.setFrom(new InternetAddress("aalae933@gmail.com", "Gold Open-Banking"));

        mailSender.send(message);
    }

}
