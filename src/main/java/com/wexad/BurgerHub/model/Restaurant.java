package com.wexad.BurgerHub.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Restaurant extends Auditable {


    private String name;
    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public static void main(String[] args) {

    }
}

