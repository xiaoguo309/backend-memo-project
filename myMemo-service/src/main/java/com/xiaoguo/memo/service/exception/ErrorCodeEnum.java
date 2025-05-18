package com.xiaoguo.memo.service.exception;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
public enum ErrorCodeEnum {
    SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
    INVALID_PARAM("INVALID_PARAM", "非法参数"),

    BACKGROUND_IMAGE_NOT_EXIST("BACKGROUND_IMAGE_NOT_EXIST", "背景图片不存在"),

    ;
    private String errorCode;
    private String errorMsg;
    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
}
