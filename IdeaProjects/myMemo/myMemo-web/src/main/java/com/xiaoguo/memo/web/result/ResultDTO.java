package com.xiaoguo.blessing.web.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 4992672318748585212L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 数据
     */
    private T data;
}
