package com.groot.base.config;

import com.groot.base.bean.result.BaseResult;
import com.groot.base.bean.result.Result;
import com.groot.business.bean.result.MemorandumResult;
import com.groot.base.exception.BusinessRuntimeException;
import com.groot.base.exception.WSRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 全局异常处理器
     *
     * @param e BusinessRuntimeException
     * @return Result.failure(异常信息)
     */
    @ExceptionHandler(value = BusinessRuntimeException.class)
    public BaseResult exceptionHandler(HttpServletResponse response, BusinessRuntimeException e) {
        e.printStackTrace();
        response.setStatus(e.getStatus());
        return BaseResult.failure(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult exceptionHandler(HttpServletResponse response, MethodArgumentNotValidException e) {
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
            return Result.failure(message.toString());
        }
        return BaseResult.failure(e.getMessage());
    }

    @ExceptionHandler(value = WSRuntimeException.class)
    public void exceptionHandler(WSRuntimeException e) throws IOException {
        if (null != e.getSession() && e.getSession().isOpen()) {
            MemorandumResult<Void> result = MemorandumResult.failure(e.getCode(), e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            e.getSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(result)));
        }
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResult exceptionHandler(HttpServletResponse response, Exception e) {
        e.printStackTrace();
        response.setStatus(400);
        return BaseResult.failure("服务器出现异常");
    }
}

