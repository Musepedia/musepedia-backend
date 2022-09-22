package com.mimiter.mgs.core.recommend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "areaName")
public class EAPriority implements Comparable<EAPriority> {

    public EAPriority(String name){
        this(name, 0);
    }

    public EAPriority(String name, int p) {
        areaName = name;
        priority = p;
    }

    String areaName;
    int priority;

    @Override
    public int compareTo(EAPriority o) {
        return o.priority - this.priority;
    } //按照优先级降序排列

}
