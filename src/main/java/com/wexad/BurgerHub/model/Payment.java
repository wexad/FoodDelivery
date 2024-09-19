package com.wexad.BurgerHub.model;

import com.wexad.BurgerHub.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment extends Auditable {


    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private PaymentType type;

    @Column(nullable = true)
    private Long cardNumber;
}

