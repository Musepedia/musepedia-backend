package com.mimiter.mgs.core.recommend;

import com.mimiter.mgs.core.recommend.model.EAPriority;
import java.util.ArrayList;

/**
 * 推荐模块添加展区操作。
 */
public interface AddOperation {

    //列表操作包括将展区加入列表、从列表删除、修改对象属性等
    void listOperation(ArrayList<EAPriority> areaList);

    //将某个展区的优先级置为最大值(100)
    void maxOperation(ArrayList<EAPriority> areaList, int maxP);
}
