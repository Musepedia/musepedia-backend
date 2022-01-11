package cn.abstractmgs.core.model.dto;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
import lombok.Data;

@Data
public class PreferenceDTO {

    private String label;

    private ExhibitionHall exhibitionHall;

    private String figureUrl;

    private boolean isHot = false;  // 是否是当前博物馆热门展品，数据库中暂无此项
}
