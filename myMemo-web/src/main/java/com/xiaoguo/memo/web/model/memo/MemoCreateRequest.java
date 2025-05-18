package com.xiaoguo.memo.web.model.memo;

import lombok.Data;

/**
 * Memo creation request model
 */
@Data
public class MemoCreateRequest {
    private String title;
    private String content;
} 