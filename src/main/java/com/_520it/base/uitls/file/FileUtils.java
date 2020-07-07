package com._520it.base.uitls.file;

import com._520it.base.common.MyFileFilter;
import com._520it.base.uitls.encryption.MD5Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @description: 文件工具类
 * @author: superman
 * @create: 2020/06/29 11:48
 */
public class FileUtils {

    /**
     * 上传文件
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * 判断是否存在上传的文件，
     * 存在，则返回文件名称
     * @return
     */
    public static String fileSrc(File targetFile){
        File file = new File("G:\\个人工具\\zuomian");
        String targeFileName = targetFile.getName();
        String suffix = targeFileName.substring(targeFileName.lastIndexOf(".") + 1);

        File[] files = file.listFiles(new MyFileFilter(targetFile.length(), suffix));

        for (File srcFile : files) {
            if(isFileEquels(srcFile,targetFile)){
                return  srcFile.getName();
            }
        }
        return null;
    }

    /**
     * 利用md5判断两个文件是否相等
     * @param srcFile
     * @param targeFile
     * @return
     */
    public static boolean isFileEquels(File srcFile,File targeFile){
        String srcStr = MD5Utils.fileMd5(srcFile);
        String targetFile = MD5Utils.fileMd5(targeFile);
        if(srcStr.equals(targetFile)){
            return true;
        }
        return false;
    }

    public static String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) {
        File file = new File("G:\\BaiduNetdiskDownload\\计划.txt");
        System.out.println(file.getPath());

        System.out.println(100);//十进制
        System.out.println(0b100);//二进制
        System.out.println(0100);//八进制
        System.out.println(0x100);//十六进制


        String src = FileUtils.fileSrc(new File("G:\\个人工具\\linyuer2.jpg"));
        System.out.println(src);

    }


}
