package com.wexad.BurgerHub.model;

import com.wexad.BurgerHub.enums.CategoryName;
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
public class Category extends Auditable {


    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CategoryName categoryName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
