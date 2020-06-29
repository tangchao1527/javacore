package com._520it.base.uitls;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: 自定义MD5工具类
 * @author: superman
 * @create: 2020/06/29 15:12
 */
public class MD5Utils {

    /**
     * 字符串的md5的值
     * @param str
     * @return
     */
    public static String strMd5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            str = byteToStr(md.digest());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }


    /**
     * 文件的md5
     * @param file
     * @return
     */
    public static String fileMd5(File file){
        MessageDigest md5 = null;
        InputStream fileInputStream = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int num = 0;
            while ((num = fileInputStream.read(buffer)) >0){
                md5.update(buffer,0,num);
            }
            fileInputStream.close();
            return byteToStr(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转为字符串
     * @return
     */
    public static String byteToStr(byte[] datas){
        int i;
        StringBuffer buf = new StringBuffer();
        for (int offset = 0; offset < datas.length; offset++) {
            i = datas[offset];
            if (i < 0){
                i += 256;
            }
            if (i < 16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
       return  buf.toString();
    }


}
