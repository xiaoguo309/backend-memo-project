package com.xiaoguo.memo.web.model.auth;

import lombok.Data;

/**
 * Login response model
 */
@Data
public class LoginResponse {
    private String token;
    private Integer userId;
    private String username;
    private Integer expiresIn;
} 