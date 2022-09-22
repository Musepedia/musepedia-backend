package com.mimiter.mgs.core.recommend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
ExhibitionArea：展区
** name：展区名
** floor：楼层
** neighbors：邻接展区表
 */
@Data
public class ExhibitionArea {
    public ExhibitionArea(String n, int f, List<String> nbs) {
        name = n;
        floor = f;
        neighbors = nbs;
    }

    public String name;
    public int floor;
    public List<String> neighbors;//相邻展区

    /*public ArrayList <String> GetNeighbors() {
        return neighbors;
    }*/
    public String[] GetNeighborsArray() {
        int s = neighbors.size();
        //ListIterator<String> it=neighbors.listIterator();
        String[] arr = new String[s];
        for (int i = 0; i < s; i++) {
            arr[i] = neighbors.get(i);
        }
        return arr;
    }
}
