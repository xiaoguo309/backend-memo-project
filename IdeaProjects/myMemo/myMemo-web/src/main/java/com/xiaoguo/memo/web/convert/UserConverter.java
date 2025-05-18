package com.xiaoguo.memo.web.convert;

import com.xiaoguo.memo.service.model.auth.UserLoginModel;
import com.xiaoguo.memo.service.model.auth.UserLoginResultModel;
import com.xiaoguo.memo.service.model.auth.UserRegisterModel;
import com.xiaoguo.memo.web.model.auth.LoginRequest;
import com.xiaoguo.memo.web.model.auth.LoginResponse;
import com.xiaoguo.memo.web.model.auth.RegisterRequest;

/**
 * Converter for user objects between service layer and web layer
 */
public class UserConverter {
    
    /**
     * Convert login request to service model
     */
    public static UserLoginModel fromLoginRequest(LoginRequest request) {
        if (request == null) {
            return null;
        }
        
        UserLoginModel model = new UserLoginModel();
        model.setUsername(request.getUsername());
        model.setPassword(request.getPassword());
        model.setRememberMe(request.getRememberMe());
        
        return model;
    }
    
    /**
     * Convert register request to service model
     */
    public static UserRegisterModel fromRegisterRequest(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        
        UserRegisterModel model = new UserRegisterModel();
        model.setUsername(request.getUsername());
        model.setPassword(request.getPassword());
        model.setConfirmPassword(request.getConfirmPassword());
        model.setAgreeTerms(request.getAgreeTerms());
        
        return model;
    }
    
    /**
     * Convert service model to login response
     */
    public static LoginResponse toLoginResponse(UserLoginResultModel model) {
        if (model == null) {
            return null;
        }
        
        LoginResponse response = new LoginResponse();
        response.setUserId(model.getUserId());
        response.setUsername(model.getUsername());
        response.setToken(model.getToken());
        response.setExpiresIn(model.getExpiresIn());
        
        return response;
    }
} 