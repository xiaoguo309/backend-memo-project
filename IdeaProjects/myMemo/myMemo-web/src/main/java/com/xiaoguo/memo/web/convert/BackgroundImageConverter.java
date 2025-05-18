package com.xiaoguo.blessing.web.convert;

import com.xiaoguo.blessing.service.model.BackgroundImage;
import com.xiaoguo.blessing.service.model.BackgroundImagePageQuery;
import com.xiaoguo.blessing.web.model.BackgroundImageDTO;
import com.xiaoguo.blessing.web.model.BackgroundImagePageQueryDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author siye.gzc
 * @date 2025/03/08
 */
public class BackgroundImageConverter {
    public static BackgroundImagePageQuery toBackgroundImagePageQuery(BackgroundImagePageQueryDTO pageQueryDTO) {
        BackgroundImagePageQuery backgroundImagePageQuery = new BackgroundImagePageQuery();
        backgroundImagePageQuery.setPageIndex(pageQueryDTO.getPageIndex());
        backgroundImagePageQuery.setPageSize(pageQueryDTO.getPageSize());
        backgroundImagePageQuery.setCreator(pageQueryDTO.getCreator());
        backgroundImagePageQuery.setModifier(pageQueryDTO.getModifier());
        return backgroundImagePageQuery;
    }

    public static List<BackgroundImageDTO> toBackgroundImageDTOList(List<BackgroundImage> backgroundImageList) {
        if (CollectionUtils.isEmpty(backgroundImageList)) {
            return new ArrayList<>();
        }
        return backgroundImageList.stream().map(BackgroundImageConverter::toBackgroundImageDTO).toList();
    }

    public static BackgroundImageDTO toBackgroundImageDTO(BackgroundImage backgroundImage) {
        BackgroundImageDTO backgroundImageDTO = new BackgroundImageDTO();
        backgroundImageDTO.setId(backgroundImage.getId());
        backgroundImageDTO.setGmtCreate(backgroundImage.getGmtCreate());
        backgroundImageDTO.setGmtModified(backgroundImage.getGmtModified());
        backgroundImageDTO.setCreator(backgroundImage.getCreator());
        backgroundImageDTO.setModifier(backgroundImage.getModifier());
        backgroundImageDTO.setImageUrl(backgroundImage.getImageUrl());
        return backgroundImageDTO;
    }
}
