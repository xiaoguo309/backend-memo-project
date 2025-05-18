package com.xiaoguo.memo.web.controller;

import com.xiaoguo.memo.service.UserService;
import com.xiaoguo.memo.service.model.auth.UserLoginModel;
import com.xiaoguo.memo.service.model.auth.UserLoginResultModel;
import com.xiaoguo.memo.service.model.auth.UserRegisterModel;
import com.xiaoguo.memo.service.result.ResultWrap;
import com.xiaoguo.memo.web.convert.UserConverter;
import com.xiaoguo.memo.web.model.auth.ForgotPasswordRequest;
import com.xiaoguo.memo.web.model.auth.LoginRequest;
import com.xiaoguo.memo.web.model.auth.LoginResponse;
import com.xiaoguo.memo.web.model.auth.RegisterRequest;
import com.xiaoguo.memo.web.result.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * User login
     */
    @PostMapping("/login")
    public ApiResult<LoginResponse> login(@RequestBody LoginRequest request) {
        UserLoginModel model = UserConverter.fromLoginRequest(request);
        ResultWrap<UserLoginResultModel> result = userService.login(model);
        
        if (!result.isSuccess()) {
            return ApiResult.error(400, result.getErrorMsg());
        }
        
        LoginResponse response = UserConverter.toLoginResponse(result.getData());
        return ApiResult.success(response);
    }

    /**
     * User registration
     */
    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody RegisterRequest request) {
        UserRegisterModel model = UserConverter.fromRegisterRequest(request);
        ResultWrap<Integer> result = userService.register(model);
        
        if (!result.isSuccess()) {
            return ApiResult.error(400, result.getErrorMsg());
        }
        
        return ApiResult.success(
                new RegisterResponse(result.getData(), request.getUsername())
        );
    }

    /**
     * User logout
     */
    @PostMapping("/logout")
    public ApiResult<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            userService.logout(token);
        }
        
        return ApiResult.success();
    }

    /**
     * Forgot password
     */
    @PostMapping("/forgot-password")
    public ApiResult<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        ResultWrap<Void> result = userService.forgotPassword(request.getEmail());
        
        if (!result.isSuccess()) {
            return ApiResult.error(400, result.getErrorMsg());
        }
        
        return ApiResult.success();
    }
    
    /**
     * Registration response inner class
     */
    private static class RegisterResponse {
        private final Integer userId;
        private final String username;
        
        public RegisterResponse(Integer userId, String username) {
            this.userId = userId;
            this.username = username;
        }
        
        public Integer getUserId() {
            return userId;
        }
        
        public String getUsername() {
            return username;
        }
    }
} 