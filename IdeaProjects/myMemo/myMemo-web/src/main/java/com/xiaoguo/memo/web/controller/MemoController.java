package com.xiaoguo.memo.web.controller;

import com.xiaoguo.memo.service.MemoService;
import com.xiaoguo.memo.service.model.memo.MemoModel;
import com.xiaoguo.memo.service.model.memo.MemoQueryModel;
import com.xiaoguo.memo.service.result.PageResultWrap;
import com.xiaoguo.memo.service.result.ResultWrap;
import com.xiaoguo.memo.web.convert.MemoConverter;
import com.xiaoguo.memo.web.model.memo.MemoCreateRequest;
import com.xiaoguo.memo.web.model.memo.MemoListResponse;
import com.xiaoguo.memo.web.model.memo.MemoResponse;
import com.xiaoguo.memo.web.model.memo.MemoUpdateRequest;
import com.xiaoguo.memo.web.result.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Memo controller
 */
@RestController
@RequestMapping("/api/memo")
@Slf4j
public class MemoController {

    @Autowired
    private MemoService memoService;

    /**
     * Get memo list
     */
    @GetMapping("/list")
    public ApiResult<MemoListResponse> getMemoList(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "gmtCreate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        
        Integer userId = (Integer) request.getAttribute("userId");
        MemoQueryModel queryModel = MemoConverter.createQueryModel(userId, page, limit, keyword, sortBy, sortOrder);
        
        PageResultWrap<MemoModel> result = memoService.getMemoList(queryModel);
        MemoListResponse response = MemoConverter.toListResponse(result);
        
        return ApiResult.success(response);
    }

    /**
     * Get memo detail
     */
    @GetMapping("/detail/{id}")
    public ApiResult<MemoResponse> getMemoDetail(HttpServletRequest request, @PathVariable Integer id) {
        Integer userId = (Integer) request.getAttribute("userId");
        ResultWrap<MemoModel> result = memoService.getMemoById(id, userId);
        
        if (!result.isSuccess()) {
            return ApiResult.error(404, result.getErrorMsg());
        }
        
        MemoResponse response = MemoConverter.toResponse(result.getData());
        return ApiResult.success(response);
    }

    /**
     * Create memo
     */
    @PostMapping("/create")
    public ApiResult<MemoResponse> createMemo(HttpServletRequest request, @RequestBody MemoCreateRequest createRequest) {
        Integer userId = (Integer) request.getAttribute("userId");
        MemoModel model = MemoConverter.fromCreateRequest(createRequest, userId);
        
        ResultWrap<Integer> result = memoService.createMemo(model);
        if (!result.isSuccess()) {
            return ApiResult.error(400, result.getErrorMsg());
        }
        
        // Get the created memo
        ResultWrap<MemoModel> memoResult = memoService.getMemoById(result.getData(), userId);
        
        return ApiResult.success(MemoConverter.toResponse(memoResult.getData()));
    }

    /**
     * Update memo
     */
    @PutMapping("/update/{id}")
    public ApiResult<MemoResponse> updateMemo(
            HttpServletRequest request,
            @PathVariable Integer id,
            @RequestBody MemoUpdateRequest updateRequest) {
        
        Integer userId = (Integer) request.getAttribute("userId");
        MemoModel model = MemoConverter.fromUpdateRequest(updateRequest, id, userId);
        
        ResultWrap<MemoModel> result = memoService.updateMemo(model, userId);
        if (!result.isSuccess()) {
            return ApiResult.error(400, result.getErrorMsg());
        }
        
        return ApiResult.success(MemoConverter.toResponse(result.getData()));
    }

    /**
     * Delete memo
     */
    @DeleteMapping("/delete/{id}")
    public ApiResult<Void> deleteMemo(HttpServletRequest request, @PathVariable Integer id) {
        Integer userId = (Integer) request.getAttribute("userId");
        ResultWrap<Void> result = memoService.deleteMemo(id, userId);
        
        if (!result.isSuccess()) {
            return ApiResult.error(400, result.getErrorMsg());
        }
        
        return ApiResult.success();
    }

    /**
     * Search memos
     */
    @GetMapping("/search")
    public ApiResult<MemoListResponse> searchMemos(
            HttpServletRequest request,
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer limit) {
        
        Integer userId = (Integer) request.getAttribute("userId");
        MemoQueryModel queryModel = MemoConverter.createQueryModel(userId, page, limit, keyword, null, null);
        
        PageResultWrap<MemoModel> result = memoService.searchMemos(queryModel);
        MemoListResponse response = MemoConverter.toListResponse(result);
        
        return ApiResult.success(response);
    }
} 