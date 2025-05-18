package com.xiaoguo.memo.web.model.memo;

import lombok.Data;

/**
 * Memo update request model
 */
@Data
public class MemoUpdateRequest {
    private String title;
    private String content;
} 