package com.chat.base.config;

import com.chat.base.bean.result.BaseResult;
import com.chat.base.bean.result.Result;
import com.chat.base.exception.BusinessRuntimeException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

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

    @ExceptionHandler(value = Exception.class)
    public BaseResult exceptionHandler(HttpServletResponse response, Exception e) {
        e.printStackTrace();
        response.setStatus(400);
        return BaseResult.failure("服务器出现异常");
    }
}

