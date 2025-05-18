package com.xiaoguo.memo.web.model.memo;

import lombok.Data;
import java.util.List;

/**
 * Memo list response model
 */
@Data
public class MemoListResponse {
    private Integer total;
    private Integer page;
    private Integer limit;
    private List<MemoResponse> list;
} 