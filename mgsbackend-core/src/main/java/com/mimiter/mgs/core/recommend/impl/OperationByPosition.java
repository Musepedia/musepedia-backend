package com.mimiter.mgs.core.recommend.impl;

import com.mimiter.mgs.core.recommend.AddOperation;
import com.mimiter.mgs.core.recommend.model.EAPriority;
import com.mimiter.mgs.core.recommend.model.ExhibitionArea;

import java.util.ArrayList;

@SuppressWarnings("MissingJavadocType")
public class OperationByPosition implements AddOperation {

    public OperationByPosition(ExhibitionArea ea) {
        position = ea;
    }

    public ExhibitionArea position;

    /**
     * 游客当前所在展区不放入展区推荐列表，如果在列表中则删除
     * 将当前所在展区的邻近展区放入列表，优先级增加3*(len-i)
     */
    @SuppressWarnings("MagicNumber")
    @Override
    public void listOperation(ArrayList<EAPriority> areaList) {
        EAPriority eap = new EAPriority(position.name);
        areaList.remove(eap);
        int len = position.neighbors.size();
        for (int i = 0; i < len; i++) {
            String area = position.neighbors.get(i);
            if (areaList.contains(new EAPriority(area))) {
                int j = areaList.indexOf(new EAPriority(area));
                EAPriority priority = areaList.get(j);
                priority.setPriority(priority.getPriority() + 3 * (len - i));
            } else {
                areaList.add(new EAPriority(area, 3 * (len - i)));
            }
        }
    }

    @Override
    public void maxOperation(ArrayList<EAPriority> areaList, int maxP) {
        //没有操作
    }
}
