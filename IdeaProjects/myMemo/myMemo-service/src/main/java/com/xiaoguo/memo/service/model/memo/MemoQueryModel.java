package com.xiaoguo.memo.service.model.memo;

import lombok.Data;

/**
 * Memo query model for service layer
 */
@Data
public class MemoQueryModel {
    private Integer userId;
    private String keyword;
    private Integer page = 1;
    private Integer limit = 20;
    private String sortBy = "gmtCreate";
    private String sortOrder = "desc";
} 