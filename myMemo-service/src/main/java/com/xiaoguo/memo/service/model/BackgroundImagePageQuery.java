package com.xiaoguo.blessing.service.model;

import lombok.Data;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class BackgroundImagePageQuery {
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
