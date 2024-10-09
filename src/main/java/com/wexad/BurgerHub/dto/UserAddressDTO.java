package com.wexad.BurgerHub.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean deleted;
    private AddressDTO address;
}
