package com.xiaoguo.memo.web.result;

import lombok.Data;
import java.io.Serializable;

/**
 * Common API response format
 */
@Data
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> error(Integer code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error(Integer code, String message, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
} 