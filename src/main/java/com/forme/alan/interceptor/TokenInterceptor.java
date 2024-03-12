package com.forme.alan.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().equals("/api/hr/login")){
            return true;
        }

        String mytoken = request.getHeader("mytoken");
        if (StringUtils.isBlank(mytoken)) {
            response.setStatus(401);
            return false;
        }else {
            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("111111")).build();
                jwtVerifier.verify(mytoken);
                return true;
            } catch (TokenExpiredException e) {
                response.setStatus(401);
                return false;
            }catch (Exception e){
                response.setStatus(401);
                return false;
            }
        }
    }
}
