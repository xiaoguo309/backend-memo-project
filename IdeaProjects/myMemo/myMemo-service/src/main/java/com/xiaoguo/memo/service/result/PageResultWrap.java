package com.xiaoguo.memo.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class PageResultWrap<T> {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 页码
     */
    private int pageIndex;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总数
     */
    private int totalCount;

    public static <T> PageResultWrap<T> ofSuccess(List<T> object, Integer totalCount, Integer pageIndex, Integer pageSize) {
        PageResultWrap<T> resultWrapper = new PageResultWrap<>();
        resultWrapper.setSuccess(true);
        resultWrapper.setData(object);
        resultWrapper.setPageIndex(pageIndex);
        resultWrapper.setPageSize(pageSize);
        resultWrapper.setTotalCount(totalCount);
        return resultWrapper;
    }
}
