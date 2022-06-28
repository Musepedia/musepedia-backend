package cn.abstractmgs.core.recommend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

@Data
public class AreaSorter {
    public AreaSorter(HashMap<String, ExhibitionArea> hm) {
        nameMap = hm;
        areaList = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public HashMap<String, ExhibitionArea> nameMap;//ExhibitionArea类不能只用一个String构造临时对象，需要存储
    public ArrayList<EAPriority> areaList;
    public ArrayList<String> visited;

    public String recommend1() {
        areaList.removeIf(area -> visited.contains(area.areaName));
        Collections.sort(areaList);
        //推荐列表中第一个
        return areaList.get(0).areaName;
    }
}
