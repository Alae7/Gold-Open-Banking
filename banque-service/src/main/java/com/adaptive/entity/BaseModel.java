package com.adaptive.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field
    private String uuid = UUID.randomUUID().toString();

    @Field
    private String code;

    @Field
    private String description;

    @Field
    private boolean isDeleted = false;

    @Field
    private boolean isStatut = true;

    @CreatedDate
    private Instant createDateTime;

    @LastModifiedDate
    private Instant updateDateTime;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseModel other = (BaseModel) obj;
        return Objects.equals(id, other.id) && Objects.equals(uuid, other.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid);
    }

}
