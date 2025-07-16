package com.adaptive.utils;



import com.adaptive.dto.NotificationRequestDto;
import com.adaptive.entity.Notification;
import com.adaptive.mapper.NotificationMapper;
import com.adaptive.model.CustomerResponseDto;
import com.adaptive.model.TransactionResponseDto;
import com.adaptive.openFeinController.CompteFeinClient;
import com.adaptive.openFeinController.CustomerFiegnClient;
import com.adaptive.openFeinController.TransactionFeinClient;
import com.adaptive.repository.NotificationRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;



public class Utils {

    private static JavaMailSender mailSender;

    private static NotificationMapper notificationMapper;

    private static NotificationRepository notificationRepository;

    private static CustomerFiegnClient customerFiegnClient;

    private static CompteFeinClient compteFeinClient;

    private static TransactionFeinClient transactionFeinClient;

    public static String sendMessage(CustomerResponseDto target, CustomerResponseDto source , TransactionResponseDto transactionResponseDto) {

        try {
            // Determine dynamic description based on transaction type
            String type = transactionResponseDto.getType();
            String description = switch (type.toUpperCase()) {
                case "VERSEMENT" -> "This payment has been successfully processed.";
                case "DEPOT" -> "Your deposit has been recorded successfully.";
                case "REMBOURSEMENT" -> "The refund has been issued successfully.";
                default -> "Your transaction has been processed.";
            };

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
                        <li><strong>Sender Module:</strong> %s  %s</li>
                        <li><strong>Receiver Module:</strong> %s  %s</li>
                        <li><strong>Status:</strong> ✅ %s</li>
                    </ul>
                
                    <p><em>%s</em></p>
                    <p>Warm regards,<br>Your Gold Open-Banking</p>
                </body>
                </html>
                """;

            String emailContent = String.format(
                    template,
                    transactionResponseDto.getType(),
                    transactionResponseDto.getUuid(),
                    transactionResponseDto.getType(),
                    transactionResponseDto.getUuid(),
                    transactionResponseDto.getType(),// e.g., Versement
                    transactionResponseDto.getUuid(),
                    transactionResponseDto.getType(),
                    transactionResponseDto.getAmount(),
                    transactionResponseDto.getCreateDateTime(),
                    target.getFirstName(),
                    target.getLastName(),
                    source.getFirstName(),
                    source.getLastName(),
                    transactionResponseDto.getStatus(),
                    description // Dynamic message based on type
            );


            // Send to receiver

            MimeMessage targetMessage  = mailSender.createMimeMessage();
            MimeMessageHelper targetHelper = new MimeMessageHelper(targetMessage , true);

            targetHelper.setTo(target.getEmail());
            targetHelper.setSubject(type + " Confirmation – " + transactionResponseDto.getUuid());
            targetHelper.setText(emailContent, true); // Enable HTML
            targetHelper.setFrom(new InternetAddress("aalae933@gmail.com", "Gold Open-Banking"));

            mailSender.send(targetMessage);

            // Send to sender

            MimeMessage sourceMessage   = mailSender.createMimeMessage();
            MimeMessageHelper sourceHelper = new MimeMessageHelper(sourceMessage  , true);

            sourceHelper.setTo(source.getEmail());
            sourceHelper.setSubject(type + " Confirmation – " + transactionResponseDto.getUuid());
            sourceHelper.setText(emailContent, true); // Enable HTML
            sourceHelper.setFrom(new InternetAddress("aalae933@gmail.com", "Gold Open-Banking"));

            mailSender.send(sourceMessage);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Failed to send emails: " + e.getMessage();
        }
        return "✅ Emails sent successfully to both sender and receiver.";
    }

    public static Notification createNotification(NotificationRequestDto notificationRequestDto) {
        Notification notification = notificationMapper.toEntity(notificationRequestDto);
        return notification;
    }

    public static CustomerResponseDto getClient(Long rib) {

        String uuid = compteFeinClient.findCustomerUuidByRib(rib);
        CustomerResponseDto customerResponseDto = customerFiegnClient.findCustomerById(uuid);
        return customerResponseDto;
    }

    public static TransactionResponseDto getTransaction(String uuid) {

        TransactionResponseDto transactionResponseDto = transactionFeinClient.findByUuid(uuid);
        return transactionResponseDto;
    }

}
