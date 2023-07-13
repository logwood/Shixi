package org.example.shixi.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.shixi.constant.MessageConstant;
import org.springframework.http.HttpStatus;

import java.lang.management.BufferPoolMXBean;

//统一了封装的数据。
@Schema(description = "响应实体")
@Data
public class RestResponse<T> {
    @Schema(description = "响应码")
    private Integer code;
    @Schema(description = "响应数据")
    private T data;
    @Schema(description = "响应消息")
    private String message;
    private RestResponse(HttpStatus httpStatus,T data,String message){
        this.code = httpStatus.value();
        this.data = data;
        this.message = message;
    }
    private RestResponse(HttpStatus httpStatus,String message){
        this.code = httpStatus.value();
        this.message = message;
    }
    public static RestResponse<Boolean> addResponse(boolean result){
        if(result){
            return ok(Boolean.TRUE, MessageConstant.ADD_OK);
        }
        return fail(Boolean.FALSE,MessageConstant.ADD_FAIL);
    }
    public static RestResponse<Boolean> deleteResponse(boolean result) {
        if (result) {
            return ok(Boolean.TRUE, MessageConstant.DELETE_OK);
        }
        return fail(Boolean.FALSE, MessageConstant.DELETE_FAIL);
    }

    public static RestResponse<Boolean> updateResponse(boolean result) {
        if (result) {
            return ok(Boolean.TRUE, MessageConstant.UPDATE_OK);
        }
        return fail(Boolean.FALSE, MessageConstant.UPDATE_FAIL);
    }
    private static<T> RestResponse<T> fail(T data,String message) {
        return new RestResponse<>(HttpStatus.OK,data,message);
    }
    public static<T> RestResponse<T> fail(String message) {
        return new RestResponse<>(HttpStatus.OK,message);
    }
    public static <T> RestResponse<T> ok(String message) {
        return new RestResponse<>(HttpStatus.OK, message);
    }

    public static <T> RestResponse<T> ok(T data, String message) {
        return new RestResponse<>(HttpStatus.OK, data, message);
    }
    public static <T> RestResponse<T> badRequest(String message) {
        return new RestResponse<>(HttpStatus.BAD_REQUEST, message);
    }

    public static <T> RestResponse<T> unauthorized(String message) {
        return new RestResponse<>(HttpStatus.UNAUTHORIZED, message);
    }

    public static <T> RestResponse<T> methodNotAllowed(String message) {
        return new RestResponse<>(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    public static <T> RestResponse<T> forbidden(String message) {
        return new RestResponse<>(HttpStatus.FORBIDDEN, message);
    }
    public static <T> RestResponse<T> queryResponse(T data) {
        return ok(data, MessageConstant.QUERY_OK);
    }

}
