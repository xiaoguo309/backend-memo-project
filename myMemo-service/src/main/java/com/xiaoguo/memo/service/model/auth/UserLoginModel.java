package com.xiaoguo.memo.service.model.auth;

import lombok.Data;

/**
 * User login model for service layer
 */
@Data
public class UserLoginModel {
    private String username;
    private String password;
    private Boolean rememberMe;
} 