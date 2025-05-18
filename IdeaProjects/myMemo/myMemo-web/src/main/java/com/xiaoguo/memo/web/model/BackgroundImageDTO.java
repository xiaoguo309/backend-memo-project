package com.xiaoguo.blessing.web.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class BackgroundImageDTO implements Serializable {
    private static final long serialVersionUID = -1548602290220584466L;

    /**
     * id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 图片url
     */
    private String imageUrl;
}
