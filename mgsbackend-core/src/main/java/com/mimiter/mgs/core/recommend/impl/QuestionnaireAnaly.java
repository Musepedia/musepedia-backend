package com.mimiter.mgs.core.recommend.impl;

import com.mimiter.mgs.core.recommend.AddOperation;
import com.mimiter.mgs.core.recommend.model.EAPriority;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class QuestionnaireAnaly implements AddOperation {
    public QuestionnaireAnaly(String... areaNames) {
        dic = new HashMap<>();
        for (String name : areaNames) {
            if (dic.containsKey(name)) {
                int v = dic.get(name);
                dic.put(name, v + 1);
            } else {
                dic.put(name, 1);
            }
        }
    }

    public HashMap<String, Integer> dic; //展区在问卷结果中出现的频数表

    /** 基于问卷的列表操作：问卷中出现的展区若列表中没有则加入，优先级增加2*频数 */
    @Override
    public void listOperation(ArrayList<EAPriority> areaList) {
        for (String name : dic.keySet()) {
            int deltaP = dic.get(name) * 2;
            if (areaList.contains(new EAPriority(name))) {
                int i = areaList.indexOf(new EAPriority(name));
                EAPriority priority = areaList.get(i);
                priority.setPriority(priority.getPriority() + deltaP);
            } else {
                areaList.add(new EAPriority(name, deltaP));
            }
        }
    }

    @Override
    public void maxOperation(ArrayList<EAPriority> areaList, int maxP) {
        //没有操作
    }
}
