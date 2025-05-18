package com.xiaoguo.memo.web.model.memo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Memo response model
 */
@Data
public class MemoResponse {
    private Integer id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
} 