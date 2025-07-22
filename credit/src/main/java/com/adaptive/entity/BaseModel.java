package com.adaptive.entity;


import com.adaptive.utils.Utils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column
    private Long id;

    @Column(name = "uuid",unique = true)
    private String uuid;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "statut")
    private CreditStatus status;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @PrePersist
    public void prePersist() {
        this.isDeleted = false;
        this.status = CreditStatus.DEMANDE;
    }


    @PostPersist
    public void postPersist() {
        if (this.getId() != null) { // Ensure the ID is generated
            String uuid = Utils.getHashedUuid(this.createDateTime.toInstant(ZoneOffset.UTC), this.getId());
            this.setUuid(uuid); // Set the UUID
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseModel other = (BaseModel) obj;
        return Objects.equals(id, other.id) && Objects.equals(uuid, other.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid);
    }



}
