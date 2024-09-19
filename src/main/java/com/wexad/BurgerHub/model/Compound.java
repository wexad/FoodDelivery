package com.wexad.BurgerHub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.wexad.BurgerHub.enums.Size;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Compound extends Auditable {

    private Double weight;

    @Enumerated(EnumType.STRING)
    private Size size;

    private Long calories;
    private Long fat;
    private Long protein;
}

