package com.chat.base.config;

import com.chat.base.exception.BusinessRuntimeException;
import com.chat.business.model.User;
import com.chat.business.utils.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String userId = "";
        String token = "";
        for (Cookie cookie : cookies) {
            if ("userId".equals(cookie.getName()) && !"".equals(cookie.getValue())) {
                userId = cookie.getValue();
                continue;
            }
            if ("token".equals(cookie.getName()) && !"".equals(cookie.getValue())) {
                token = cookie.getValue();
            }
        }
        if (!userId.equals("") && !token.equals("")) {
            User user = JWTUtil.verifyToken(token);
            if (userId.equals(user.getId())) {
                return  true;
            }
        }
        throw new BusinessRuntimeException("登录状态校验失败，请重新登录", 401);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
