package com.xiaoguo.memo.service.model.auth;

import lombok.Data;

/**
 * User login result model for service layer
 */
@Data
public class UserLoginResultModel {
    private String token;
    private Integer userId;
    private String username;
    private Integer expiresIn;
} 