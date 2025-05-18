package com.xiaoguo.blessing.web.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
@Data
public class PageResultDTO<T> implements Serializable {
    private static final long serialVersionUID = -6332804888075975588L;

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

    /**
     * 总页数
     * @return
     */
    public int getTotalPage() {
        return totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
    }

    public static <T> PageResultDTO<T> ofSuccess(List<T> object, Integer totalCount, Integer pageIndex, Integer pageSize) {
        PageResultDTO<T> pageResultDTO = new PageResultDTO<>();
        pageResultDTO.setSuccess(true);
        pageResultDTO.setData(object);
        pageResultDTO.setPageIndex(pageIndex);
        pageResultDTO.setPageSize(pageSize);
        pageResultDTO.setTotalCount(totalCount);
        return pageResultDTO;
    }
}
