package com.xiaoguo.memo.service;

import com.xiaoguo.memo.service.model.auth.UserLoginModel;
import com.xiaoguo.memo.service.model.auth.UserLoginResultModel;
import com.xiaoguo.memo.service.model.auth.UserRegisterModel;
import com.xiaoguo.memo.service.result.ResultWrap;

/**
 * User service interface
 */
public interface UserService {
    
    /**
     * User login
     * @param loginModel login parameters
     * @return login result with token
     */
    ResultWrap<UserLoginResultModel> login(UserLoginModel loginModel);
    
    /**
     * User registration
     * @param registerModel registration parameters
     * @return registration result
     */
    ResultWrap<Integer> register(UserRegisterModel registerModel);
    
    /**
     * User logout
     * @param token user token
     * @return logout result
     */
    ResultWrap<Void> logout(String token);
    
    /**
     * Request password reset
     * @param email user email
     * @return password reset request result
     */
    ResultWrap<Void> forgotPassword(String email);
    
    /**
     * Validate token
     * @param token user token
     * @return user id if token is valid
     */
    ResultWrap<Integer> validateToken(String token);
} 