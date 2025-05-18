package com.xiaoguo.memo.service;

import com.xiaoguo.memo.service.model.memo.MemoModel;
import com.xiaoguo.memo.service.model.memo.MemoQueryModel;
import com.xiaoguo.memo.service.result.PageResultWrap;
import com.xiaoguo.memo.service.result.ResultWrap;

/**
 * Memo service interface
 */
public interface MemoService {
    
    /**
     * Get memo list by query parameters
     * @param queryModel query parameters
     * @return memo list
     */
    PageResultWrap<MemoModel> getMemoList(MemoQueryModel queryModel);
    
    /**
     * Get memo detail by id
     * @param memoId memo id
     * @param userId user id
     * @return memo detail
     */
    ResultWrap<MemoModel> getMemoById(Integer memoId, Integer userId);
    
    /**
     * Create a new memo
     * @param memo memo to create
     * @return created memo id
     */
    ResultWrap<Integer> createMemo(MemoModel memo);
    
    /**
     * Update an existing memo
     * @param memo memo to update
     * @param userId user id
     * @return update result
     */
    ResultWrap<MemoModel> updateMemo(MemoModel memo, Integer userId);
    
    /**
     * Delete a memo
     * @param memoId memo id
     * @param userId user id
     * @return delete result
     */
    ResultWrap<Void> deleteMemo(Integer memoId, Integer userId);
    
    /**
     * Search memos by keyword
     * @param queryModel query parameters
     * @return memo list
     */
    PageResultWrap<MemoModel> searchMemos(MemoQueryModel queryModel);
} 