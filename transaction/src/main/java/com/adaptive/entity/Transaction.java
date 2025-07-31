package com.adaptive.entity;



import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Transaction extends BaseModel {

    private Double amount;

    private TypeTransaction type;

    private Long targetRib;

    private Long sourceRib;

}
