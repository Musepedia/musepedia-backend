package com.mimiter.mgs.core.recommend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ExhibitionArea：展区
 * name：展区名
 * floor：楼层
 * neighbors：邻接展区表
 */
@Data
@AllArgsConstructor
public class ExhibitionArea {

    public String name;
    public int floor;
    public List<String> neighbors; // 相邻展区

    /**
     * 获取邻接矩阵
     *
     * @return 邻接矩阵
     */
    public String[] getNeighborsArray() {
        int s = neighbors.size();
        String[] arr = new String[s];
        for (int i = 0; i < s; i++) {
            arr[i] = neighbors.get(i);
        }
        return arr;
    }
}
