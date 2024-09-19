package com.wexad.BurgerHub.model;

import com.wexad.BurgerHub.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Getter(AccessLevel.PUBLIC)
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role extends Auditable {
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private RoleName name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<AuthUser> users;

}
