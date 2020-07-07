package com._520it.base.uitls.other;

import java.util.*;

/**
 * 多个数组之间的操作
 */
/**
 * @description: 数组操作
 * @author: superman
 * @create: 2020/06/03 18:25
 */
public class ManyArrayOperation {


    /**
     * 并集，去重
     * @return
     */
    public static Integer[] union(Integer[] a,Integer[] b){
        //利用set 不重复元素特性
        TreeSet<Integer> set = new TreeSet<>();
        set.addAll(Arrays.asList(a));
        set.addAll(Arrays.asList(b));

        Integer[] integers = set.toArray(new Integer[set.size()]);
        System.out.println(Arrays.toString(integers));
        return integers;
    }

    /**
     * 数组交集
     * @param m
     * @param n
     * @return
     */
    public static Integer[] intersection(Integer[] m,Integer[] n){
        List<Integer> rs = new ArrayList<Integer>();

        // 将较长的数组转换为set
        Set<Integer> set = new HashSet<Integer>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Integer i : m.length > n.length ? n : m) {
            if (set.contains(i))
            {
                rs.add(i);
            }
        }

        Integer[] arr = {};
        return rs.toArray(arr);
    }

    /**
     * 求差集
     * @param m
     * @param n
     * @return
     */
    private static Integer[] getC(Integer[] m, Integer[] n)
    {
        // 将较长的数组转换为set
        Set<Integer> set = new HashSet<Integer>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Integer i : m.length > n.length ? n : m)
        {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i))
            {
                set.remove(i);
            } else
            {
                set.add(i);
            }
        }

        Integer[] arr = {};
        return set.toArray(arr);
    }

}
