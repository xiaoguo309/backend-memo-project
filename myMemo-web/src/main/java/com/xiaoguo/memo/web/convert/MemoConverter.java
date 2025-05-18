package com.xiaoguo.memo.web.convert;

import com.xiaoguo.memo.service.model.memo.MemoModel;
import com.xiaoguo.memo.service.model.memo.MemoQueryModel;
import com.xiaoguo.memo.service.result.PageResultWrap;
import com.xiaoguo.memo.web.model.memo.MemoCreateRequest;
import com.xiaoguo.memo.web.model.memo.MemoListResponse;
import com.xiaoguo.memo.web.model.memo.MemoResponse;
import com.xiaoguo.memo.web.model.memo.MemoUpdateRequest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter for memo objects between service layer and web layer
 */
public class MemoConverter {
    
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    
    /**
     * Convert service model to web response
     */
    public static MemoResponse toResponse(MemoModel model) {
        if (model == null) {
            return null;
        }
        
        MemoResponse response = new MemoResponse();
        response.setId(model.getId());
        response.setTitle(model.getTitle());
        response.setContent(model.getContent());
        
        if (model.getGmtCreate() != null) {
            response.setCreatedAt(model.getGmtCreate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .format(ISO_FORMATTER));
        }
        
        if (model.getGmtModified() != null) {
            response.setUpdatedAt(model.getGmtModified().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .format(ISO_FORMATTER));
        }
        
        return response;
    }
    
    /**
     * Convert create request to service model
     */
    public static MemoModel fromCreateRequest(MemoCreateRequest request, Integer userId) {
        if (request == null) {
            return null;
        }
        
        MemoModel model = new MemoModel();
        model.setUserId(userId);
        model.setTitle(request.getTitle());
        model.setContent(request.getContent());
        
        return model;
    }
    
    /**
     * Convert update request to service model
     */
    public static MemoModel fromUpdateRequest(MemoUpdateRequest request, Integer memoId, Integer userId) {
        if (request == null) {
            return null;
        }
        
        MemoModel model = new MemoModel();
        model.setId(memoId);
        model.setUserId(userId);
        model.setTitle(request.getTitle());
        model.setContent(request.getContent());
        
        return model;
    }
    
    /**
     * Create query model from parameters
     */
    public static MemoQueryModel createQueryModel(Integer userId, Integer page, Integer limit, 
                                                 String keyword, String sortBy, String sortOrder) {
        MemoQueryModel queryModel = new MemoQueryModel();
        queryModel.setUserId(userId);
        
        if (page != null && page > 0) {
            queryModel.setPage(page);
        }
        
        if (limit != null && limit > 0) {
            queryModel.setLimit(limit);
        }
        
        if (keyword != null) {
            queryModel.setKeyword(keyword);
        }
        
        if (sortBy != null) {
            queryModel.setSortBy(sortBy);
        }
        
        if (sortOrder != null) {
            queryModel.setSortOrder(sortOrder);
        }
        
        return queryModel;
    }
    
    /**
     * Convert page result to list response
     */
    public static MemoListResponse toListResponse(PageResultWrap<MemoModel> pageResult) {
        if (pageResult == null || !pageResult.isSuccess()) {
            return new MemoListResponse();
        }
        
        List<MemoResponse> responses = pageResult.getData().stream()
                .map(MemoConverter::toResponse)
                .collect(Collectors.toList());
        
        MemoListResponse response = new MemoListResponse();
        response.setTotal(pageResult.getTotalCount());
        response.setPage(pageResult.getPageIndex());
        response.setLimit(pageResult.getPageSize());
        response.setList(responses);
        
        return response;
    }
} 