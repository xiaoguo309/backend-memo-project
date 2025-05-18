package com.xiaoguo.memo.web.model.auth;

import lombok.Data;

/**
 * Register request model
 */
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private Boolean agreeTerms;
} 