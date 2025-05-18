package com.xiaoguo.blessing.service.model;

import lombok.Data;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class BackgroundImageUpsert {
    /**
     * id
     */
    private Integer id;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 图片url
     */
    private String imageUrl;
}
