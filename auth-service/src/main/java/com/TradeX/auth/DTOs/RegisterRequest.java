package com.TradeX.auth.DTOs;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
