package com.xiaoguo.memo.service.exception;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
public class BizException extends RuntimeException{
    private String errorCode;

    public BizException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorMsg());
        this.errorCode = errorCodeEnum.getErrorCode();
    }

    public BizException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
