package com.xiaoguo.memo.service.model.auth;

import lombok.Data;

/**
 * User registration model for service layer
 */
@Data
public class UserRegisterModel {
    private String username;
    private String password;
    private String confirmPassword;
    private Boolean agreeTerms;
} 