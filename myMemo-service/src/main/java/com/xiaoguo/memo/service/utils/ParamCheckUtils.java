package com.xiaoguo.memo.service.utils;

import com.xiaoguo.memo.service.exception.BizException;
import com.xiaoguo.memo.service.exception.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
public class ParamCheckUtils {

    public static void checkTrue(boolean condition) {
        if (!condition) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM);
        }
    }

    public static void checkTrue(boolean condition, String errorMsg) {
        if (!condition) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM.getErrorCode(), Optional.of(errorMsg).orElse("参数不能为空"));
        }
    }

    public static void checkNotBlank(String str) {
        if (StringUtils.isBlank(str)) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM);
        }
    }

    public static void checkNotBlank(String str, String errorMsg) {
        if (StringUtils.isBlank(str)) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM.getErrorCode(), Optional.of(errorMsg).orElse("参数不能为空"));
        }
    }

    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM);
        }
    }

    public static void checkNotNull(Object obj, String errorMsg) {
        if (obj == null) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM.getErrorCode(), Optional.of(errorMsg).orElse("参数不能为空"));
        }
    }
}
