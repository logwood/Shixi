package org.example.shixi.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.shixi.controller.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public RestResponse<Void> exception(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        String message = fieldErrorList.get(0).getDefaultMessage();
        log.error("页面传参错误：{}", message);
        return RestResponse.badRequest(message);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public RestResponse<String> exception(IllegalArgumentException e) {
        log.error("后台传参错误：", e);
        return RestResponse.badRequest(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ServiceException.class)
    public RestResponse<String> exception(ServiceException e) {
        log.error("错误信息：", e);
        return RestResponse.fail(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = AuthenticationServiceException.class)
    public RestResponse<String> exception(AuthenticationServiceException e) {
        return RestResponse.methodNotAllowed(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public RestResponse<String> exception(BadCredentialsException e) {
        return RestResponse.unauthorized(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public RestResponse<String> exception(AccessDeniedException e) {
        return RestResponse.forbidden(e.getMessage());
    }

}
