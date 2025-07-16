package com.adaptive.entity;


import com.adaptive.utils.Utils;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Id;

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

    @Column(name = "rib",length = 15, unique = true)
    private Long rib;

    @Column(name = "cle")
    private int cle;


    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted", nullable = true,columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "is_statut", nullable = true,columnDefinition = "boolean default true")
    private boolean isStatut;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;


    @PreRemove
    public void preRemove() {

    }

    @PreDestroy
    public void preDestroy() {

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
        return Objects.equals(id, other.id) && Objects.equals(rib, other.rib);
    }



}
