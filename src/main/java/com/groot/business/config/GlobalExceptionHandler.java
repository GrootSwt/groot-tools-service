package com.groot.business.config;

import com.groot.business.bean.response.base.Response;
import com.groot.business.bean.response.base.WSResponse;
import com.groot.business.exception.BusinessRuntimeException;
import com.groot.business.exception.WSRuntimeException;

import cn.dev33.satoken.exception.NotLoginException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.List;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    /**
     * 全局异常处理器
     *
     * @param e BusinessRuntimeException
     * @return Result.failure(异常信息)
     */
    @ExceptionHandler(value = BusinessRuntimeException.class)
    public Response<Void> exceptionHandler(HttpServletResponse response, BusinessRuntimeException e) {
        e.printStackTrace();
        response.setStatus(e.getStatus());
        return Response.failure(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response<Void> exceptionHandler(HttpServletResponse response, MethodArgumentNotValidException e) {
        e.printStackTrace();
        response.setStatus(400);
        int errorCount = e.getBindingResult().getErrorCount();
        if (0 != errorCount) {
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < errorCount; i++) {
                message.append(errors.get(i).getDefaultMessage());
                if (i != errorCount - 1) {
                    message.append("\n");
                }
            }
            return Response.failure(message.toString());
        }
        return Response.failure(e.getMessage());
    }

    @ExceptionHandler(value = WSRuntimeException.class)
    public void exceptionHandler(WSRuntimeException e) throws IOException {
        if (null != e.getSession() && e.getSession().isOpen()) {
            WSResponse<?, ?> response = WSResponse.failure(e.getCode(), e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            e.getSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
        }
    }

    @ExceptionHandler(value = NotLoginException.class)
    public Response<Void> exceptionHandler(HttpServletResponse response, NotLoginException e) {
        e.printStackTrace();
        response.setStatus(401);
        return Response.failure("登陆状态异常，请重新登录");
    }

    @ExceptionHandler(value = Exception.class)
    public Response<Void> exceptionHandler(HttpServletResponse response, Exception e) {
        e.printStackTrace();
        response.setStatus(500);
        return Response.failure("服务器出现异常");
    }
}

