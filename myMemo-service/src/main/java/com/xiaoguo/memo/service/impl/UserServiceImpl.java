package com.xiaoguo.memo.service.impl;

import com.xiaoguo.memo.dao.dataobj.UserDO;
import com.xiaoguo.memo.dao.dataobj.UserDOParam;
import com.xiaoguo.memo.dao.mapper.UserMapper;
import com.xiaoguo.memo.service.UserService;
import com.xiaoguo.memo.service.model.auth.UserLoginModel;
import com.xiaoguo.memo.service.model.auth.UserLoginResultModel;
import com.xiaoguo.memo.service.model.auth.UserRegisterModel;
import com.xiaoguo.memo.service.result.ResultWrap;
import com.xiaoguo.memo.service.utils.JwtTokenUtil;
import com.xiaoguo.memo.service.utils.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User service implementation
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    private static final int DEFAULT_TOKEN_EXPIRES_IN = 86400; // 24 hours in seconds

    @Override
    public ResultWrap<UserLoginResultModel> login(UserLoginModel loginModel) {
        if (loginModel == null || StringUtils.isBlank(loginModel.getUsername()) 
                || StringUtils.isBlank(loginModel.getPassword())) {
            return ResultWrap.ofFail("INVALID_PARAMS", "Username and password are required");
        }
        
        UserDOParam param = new UserDOParam();
        param.createCriteria().andUserNameEqualTo(loginModel.getUsername());
        List<UserDO> users = userMapper.selectByExample(param);
        
        if (users.isEmpty()) {
            return ResultWrap.ofFail("LOGIN_FAILED", "Invalid username or password");
        }
        
        UserDO user = users.get(0);
        if (!PasswordEncoder.matches(loginModel.getPassword(), user.getPassword())) {
            return ResultWrap.ofFail("LOGIN_FAILED", "Invalid username or password");
        }
        
        // Generate JWT token
        String token = JwtTokenUtil.generateToken(user.getId().toString(), 
                loginModel.getRememberMe() != null && loginModel.getRememberMe() ? 30 : 1);
        
        UserLoginResultModel result = new UserLoginResultModel();
        result.setUserId(user.getId());
        result.setUsername(user.getUserName());
        result.setToken(token);
        result.setExpiresIn(DEFAULT_TOKEN_EXPIRES_IN);
        
        return ResultWrap.ofSuccess(result);
    }

    @Override
    public ResultWrap<Integer> register(UserRegisterModel registerModel) {
        if (registerModel == null || StringUtils.isBlank(registerModel.getUsername()) 
                || StringUtils.isBlank(registerModel.getPassword()) 
                || StringUtils.isBlank(registerModel.getConfirmPassword())) {
            return ResultWrap.ofFail("INVALID_PARAMS", "Username and password are required");
        }
        
        if (!registerModel.getPassword().equals(registerModel.getConfirmPassword())) {
            return ResultWrap.ofFail("PASSWORD_MISMATCH", "Passwords do not match");
        }
        
        if (registerModel.getAgreeTerms() == null || !registerModel.getAgreeTerms()) {
            return ResultWrap.ofFail("TERMS_NOT_AGREED", "You must agree to the terms of service");
        }
        
        // Check if username already exists
        UserDOParam param = new UserDOParam();
        param.createCriteria().andUserNameEqualTo(registerModel.getUsername());
        long count = userMapper.countByExample(param);
        
        if (count > 0) {
            return ResultWrap.ofFail("USERNAME_EXISTS", "Username already exists");
        }
        
        // Create new user
        UserDO user = new UserDO();
        user.setUserName(registerModel.getUsername());
        user.setPassword(PasswordEncoder.encode(registerModel.getPassword()));
        user.setStatus(1); // Active
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        
        userMapper.insertSelective(user);
        
        return ResultWrap.ofSuccess(user.getId());
    }

    public static void main(String[] args) {
        System.out.println(PasswordEncoder.encode("123456"));
    }

    @Override
    public ResultWrap<Void> logout(String token) {
        // In a real implementation, we would invalidate the token
        // For a simple JWT implementation, we would typically add the token to a blacklist
        // or use a Redis cache to invalidate it
        
        return ResultWrap.ofSuccess();
    }

    @Override
    public ResultWrap<Void> forgotPassword(String email) {
        if (StringUtils.isBlank(email)) {
            return ResultWrap.ofFail("INVALID_PARAMS", "Email is required");
        }
        
        // Check if user exists
        UserDOParam param = new UserDOParam();
        param.createCriteria().andUserNameEqualTo(email);
        long count = userMapper.countByExample(param);
        
        if (count == 0) {
            return ResultWrap.ofFail("USER_NOT_FOUND", "User not found");
        }
        
        // In a real implementation, we would send an email with a password reset link
        // For now, we'll just return success
        
        return ResultWrap.ofSuccess();
    }

    @Override
    public ResultWrap<Integer> validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            return ResultWrap.ofFail("INVALID_TOKEN", "Token is required");
        }
        
        String userId = JwtTokenUtil.validateTokenAndGetUserId(token);
        if (userId == null) {
            return ResultWrap.ofFail("INVALID_TOKEN", "Invalid or expired token");
        }
        
        return ResultWrap.ofSuccess(Integer.valueOf(userId));
    }
} 