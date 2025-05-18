package com.xiaoguo.memo.service.result;

import lombok.Data;

/**
 * Service layer result wrapper
 */
@Data
public class ResultWrap<T> {
    private boolean success;
    private String errorCode;
    private String errorMsg;
    private T data;

    public static <T> ResultWrap<T> ofSuccess(T data) {
        ResultWrap<T> result = new ResultWrap<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> ResultWrap<T> ofSuccess() {
        return ofSuccess(null);
    }

    public static <T> ResultWrap<T> ofFail(String errorCode, String errorMsg) {
        ResultWrap<T> result = new ResultWrap<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        return result;
    }
} 