package com.xiaoguo.memo.web.model.auth;

import lombok.Data;

/**
 * Login request model
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
    private Boolean rememberMe;
} 