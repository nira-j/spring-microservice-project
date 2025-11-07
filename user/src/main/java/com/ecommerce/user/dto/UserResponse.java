package com.ecommerce.user.dto;

import com.ecommerce.user.models.Role;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String keyCloakId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
    private AddressDTO address;
}
