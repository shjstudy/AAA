package com.invengo.xcrf.view;

import java.util.Comparator;

/**
 *定义按数字排序算法
 * @author black
 * @since 2011-10-20
 */
public class OrderNumberComparator implements Comparator<Object>{

    public int compare(Object o1, Object o2) {
        int i = Integer.parseInt((String)o1) - Integer.parseInt((String)o2);
            return i;
    }

}
