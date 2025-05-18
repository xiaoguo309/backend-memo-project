package com.xiaoguo.memo.web.model.auth;

import lombok.Data;

/**
 * Forgot password request model
 */
@Data
public class ForgotPasswordRequest {
    private String email;
} 