package com.xiaoguo.memo.service.model.memo;

import lombok.Data;
import java.util.Date;

/**
 * Memo model for service layer
 */
@Data
public class MemoModel {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private Date gmtCreate;
    private Date gmtModified;
} 