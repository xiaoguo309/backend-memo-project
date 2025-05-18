package com.xiaoguo.memo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaoguo.memo.dao.dataobj.MemoDO;
import com.xiaoguo.memo.dao.dataobj.MemoDOParam;
import com.xiaoguo.memo.dao.mapper.MemoMapper;
import com.xiaoguo.memo.service.MemoService;
import com.xiaoguo.memo.service.model.memo.MemoModel;
import com.xiaoguo.memo.service.model.memo.MemoQueryModel;
import com.xiaoguo.memo.service.result.PageResultWrap;
import com.xiaoguo.memo.service.result.ResultWrap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Memo service implementation
 */
@Service
@Slf4j
public class MemoServiceImpl implements MemoService {

    @Autowired
    private MemoMapper memoMapper;

    @Override
    public PageResultWrap<MemoModel> getMemoList(MemoQueryModel queryModel) {
        if (queryModel == null || queryModel.getUserId() == null) {
            return PageResultWrap.ofSuccess(new ArrayList<>(), 0, 1, 20);
        }
        
        MemoDOParam param = new MemoDOParam();
        param.createCriteria()
                .andUserIdEqualTo(queryModel.getUserId())
                .andStatusEqualTo(1); // Only active memos
        
        // Set up sorting
        if ("asc".equalsIgnoreCase(queryModel.getSortOrder())) {
            if ("title".equals(queryModel.getSortBy())) {
                param.setOrderByClause("title asc");
            } else {
                param.setOrderByClause("gmt_create asc");
            }
        } else {
            if ("title".equals(queryModel.getSortBy())) {
                param.setOrderByClause("title desc");
            } else {
                param.setOrderByClause("gmt_create desc");
            }
        }
        
        // Setup pagination
        Page<MemoDO> page = PageHelper.startPage(queryModel.getPage(), queryModel.getLimit());
        List<MemoDO> memos = memoMapper.selectByExampleWithBLOBs(param);
        
        // Convert to model objects
        List<MemoModel> result = memos.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
        
        return PageResultWrap.ofSuccess(result, (int) page.getTotal(), queryModel.getPage(), queryModel.getLimit());
    }

    @Override
    public ResultWrap<MemoModel> getMemoById(Integer memoId, Integer userId) {
        if (memoId == null || userId == null) {
            return ResultWrap.ofFail("INVALID_PARAMS", "Memo ID and user ID are required");
        }
        
        MemoDO memo = memoMapper.selectByPrimaryKey(memoId);
        if (memo == null || !memo.getUserId().equals(userId) || memo.getStatus() != 1) {
            return ResultWrap.ofFail("MEMO_NOT_FOUND", "Memo not found or access denied");
        }
        
        return ResultWrap.ofSuccess(convertToModel(memo));
    }

    @Override
    public ResultWrap<Integer> createMemo(MemoModel memo) {
        if (memo == null || memo.getUserId() == null || StringUtils.isBlank(memo.getTitle())) {
            return ResultWrap.ofFail("INVALID_PARAMS", "User ID and title are required");
        }
        
        MemoDO memoDO = new MemoDO();
        memoDO.setUserId(memo.getUserId());
        memoDO.setTitle(memo.getTitle());
        memoDO.setContent(memo.getContent());
        memoDO.setStatus(1); // Active
        memoDO.setGmtCreate(new Date());
        memoDO.setGmtModified(new Date());
        
        memoMapper.insertSelective(memoDO);
        
        return ResultWrap.ofSuccess(memoDO.getId());
    }

    @Override
    public ResultWrap<MemoModel> updateMemo(MemoModel memo, Integer userId) {
        if (memo == null || memo.getId() == null || userId == null) {
            return ResultWrap.ofFail("INVALID_PARAMS", "Memo ID and user ID are required");
        }
        
        // Check if memo exists and belongs to the user
        MemoDO existingMemo = memoMapper.selectByPrimaryKey(memo.getId());
        if (existingMemo == null || !existingMemo.getUserId().equals(userId) || existingMemo.getStatus() != 1) {
            return ResultWrap.ofFail("MEMO_NOT_FOUND", "Memo not found or access denied");
        }
        
        // Update memo
        MemoDO memoDO = new MemoDO();
        memoDO.setId(memo.getId());
        if (StringUtils.isNotBlank(memo.getTitle())) {
            memoDO.setTitle(memo.getTitle());
        }
        if (memo.getContent() != null) {
            memoDO.setContent(memo.getContent());
        }
        memoDO.setGmtModified(new Date());
        
        memoMapper.updateByPrimaryKeySelective(memoDO);
        
        // Fetch updated memo
        MemoDO updatedMemo = memoMapper.selectByPrimaryKey(memo.getId());
        
        return ResultWrap.ofSuccess(convertToModel(updatedMemo));
    }

    @Override
    public ResultWrap<Void> deleteMemo(Integer memoId, Integer userId) {
        if (memoId == null || userId == null) {
            return ResultWrap.ofFail("INVALID_PARAMS", "Memo ID and user ID are required");
        }
        
        // Check if memo exists and belongs to the user
        MemoDO existingMemo = memoMapper.selectByPrimaryKey(memoId);
        if (existingMemo == null || !existingMemo.getUserId().equals(userId) || existingMemo.getStatus() != 1) {
            return ResultWrap.ofFail("MEMO_NOT_FOUND", "Memo not found or access denied");
        }
        
        // Soft delete: update status
        MemoDO memoDO = new MemoDO();
        memoDO.setId(memoId);
        memoDO.setStatus(0); // Inactive/deleted
        memoDO.setGmtModified(new Date());
        
        memoMapper.updateByPrimaryKeySelective(memoDO);
        
        return ResultWrap.ofSuccess();
    }

    @Override
    public PageResultWrap<MemoModel> searchMemos(MemoQueryModel queryModel) {
        if (queryModel == null || queryModel.getUserId() == null || StringUtils.isBlank(queryModel.getKeyword())) {
            return PageResultWrap.ofSuccess(new ArrayList<>(), 0, 1, 20);
        }
        
        MemoDOParam param = new MemoDOParam();
        param.createCriteria()
                .andUserIdEqualTo(queryModel.getUserId())
                .andStatusEqualTo(1) // Only active memos
                .andTitleLike("%" + queryModel.getKeyword() + "%");
        
        // Setup pagination
        Page<MemoDO> page = PageHelper.startPage(queryModel.getPage(), queryModel.getLimit());
        List<MemoDO> memos = memoMapper.selectByExampleWithBLOBs(param);
        
        // If no results found by title, search by content
        if (memos.isEmpty()) {
            param = new MemoDOParam();
            param.createCriteria()
                    .andUserIdEqualTo(queryModel.getUserId())
                    .andStatusEqualTo(1); // Only active memos
            
            page = PageHelper.startPage(queryModel.getPage(), queryModel.getLimit());
            List<MemoDO> contentMemos = memoMapper.selectByExampleWithBLOBs(param);
            
            // Filter by content manually
            memos = contentMemos.stream()
                    .filter(memo -> memo.getContent() != null && 
                            memo.getContent().contains(queryModel.getKeyword()))
                    .collect(Collectors.toList());
        }
        
        // Convert to model objects
        List<MemoModel> result = memos.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
        
        return PageResultWrap.ofSuccess(result, result.size(), queryModel.getPage(), queryModel.getLimit());
    }
    
    /**
     * Convert DAO object to service model
     */
    private MemoModel convertToModel(MemoDO memoDO) {
        if (memoDO == null) {
            return null;
        }
        
        MemoModel model = new MemoModel();
        model.setId(memoDO.getId());
        model.setUserId(memoDO.getUserId());
        model.setTitle(memoDO.getTitle());
        model.setContent(memoDO.getContent());
        model.setGmtCreate(memoDO.getGmtCreate());
        model.setGmtModified(memoDO.getGmtModified());
        
        return model;
    }
} 