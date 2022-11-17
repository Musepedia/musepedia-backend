package com.mimiter.mgs.core.recommend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * 用于排序的区域。
 */
@Data
public class AreaSorter {

    public HashMap<String, ExhibitionArea> nameMap; // ExhibitionArea类不能只用一个String构造临时对象，需要存储
    public ArrayList<EAPriority> areaList;
    public ArrayList<String> visited;

    /**
     * /
     *
     * @param hm 用于存储区域名和区域对象的映射
     */
    public AreaSorter(HashMap<String, ExhibitionArea> hm) {
        nameMap = hm;
        areaList = new ArrayList<>();
        visited = new ArrayList<>();
    }

    /**
     * 推荐一个展区。
     *
     * @return 推荐的展区名
     */
    public String recommend1() {
        areaList.removeIf(area -> visited.contains(area.areaName));
        Collections.sort(areaList);
        //推荐列表中第一个
        return areaList.get(0).areaName;
    }
}
