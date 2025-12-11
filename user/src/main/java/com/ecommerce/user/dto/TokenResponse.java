package com.ecommerce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
	private String username;
	private String role;
    private String token;
}
