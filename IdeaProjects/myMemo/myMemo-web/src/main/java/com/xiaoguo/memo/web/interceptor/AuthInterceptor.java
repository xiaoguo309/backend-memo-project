package com.xiaoguo.memo.web.interceptor;

import com.xiaoguo.memo.service.UserService;
import com.xiaoguo.memo.service.result.ResultWrap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Authentication interceptor
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get token from header
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\":401,\"message\":\"Unauthorized\",\"data\":null}");
            return false;
        }
        
        String token = authHeader.substring(7);
        ResultWrap<Integer> result = userService.validateToken(token);
        
        if (!result.isSuccess()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"code\":401,\"message\":\"Invalid token\",\"data\":null}");
            return false;
        }
        
        // Store user ID in request for controllers to use
        request.setAttribute("userId", result.getData());
        return true;
    }
} 