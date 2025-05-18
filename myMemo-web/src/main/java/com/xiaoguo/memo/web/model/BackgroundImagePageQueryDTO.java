package com.xiaoguo.blessing.web.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class BackgroundImagePageQueryDTO implements Serializable {
    private static final long serialVersionUID = -6521285911685292033L;

    /**
     * 页码(从1开始)
     */
    private int pageIndex;

    /**
     * 每页大小
     */
    private int pageSize;

    private String creator;

    private String modifier;
}
