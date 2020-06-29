package com._520it.base.common;

import java.io.File;
import java.io.FileFilter;

/**
 * @description: 文件过滤条件
 * @author: superman
 * @create: 2020/06/29 14:46
 */
public class MyFileFilter implements FileFilter {

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件类型
     */
    private String suffix;

    public MyFileFilter(long size,String suffix){
        this.size = size;
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File file) {
        if(file.length() != size ){
            return false;
        }

        String name = file.getName();
        if(! suffix.equals(name.substring(name.lastIndexOf(".")+1))){
            return false;
        }

        return true;
    }
}
