package com._520it.base.uitls.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 本地内存缓存
 * @author: superman
 * @create: 2020/07/10 15:48
 */
public class LocalCacheUtils {

    private static final Map<String,Object> cache = new ConcurrentHashMap<>();
    private static final Map<String,Long>  cacheExpireTime = new ConcurrentHashMap<>();
    private static final Map<String,Long>  cacheTime = new ConcurrentHashMap<>();
    private static final Map<String,Date>  cacheDate = new ConcurrentHashMap<>();
    public  static final long  CLEARTIME = 1000*60*30;//默认缓存时间


    /**
     * 设置缓存,有过期时间
     * @param key
     * @param value
     * @param expireTime 毫秒
     */
    public static void setCache(String key,Object value,long expireTime){
        cache.put(key,value);
        cacheExpireTime.put(key,expireTime);
        cacheTime.put(key,System.currentTimeMillis());
    }

    /**
     * 设置缓存,定时缓存
     * @param key
     * @param value
     * @param date 日期
     */
    public static void setDateCache(String key, Object value, Date date)  {
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        cache.put(key,value);
        try {
            cacheDate.put(key,sdfDay.parse(sdfDay.format(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取定时日期的缓存
     * @param key
     * @return
     */
    public static Object getDateCache(String key){
        if(isExist(key)){
            Date expireDate = cacheDate.get(key);
            SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date nowDate = sdfDay.parse(sdfDay.format(new Date()));
                if(expireDate.getTime() >= nowDate.getTime()){
                    return cache.get(key);
                }
                cache.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置缓存，不过期
     * @param key
     * @param value
     */
    public static void setCache(String key,Object value){
        cache.put(key,value);
        cacheExpireTime.put(key,-1L);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static Object getCache(String key){
        if(!isExpire(key)){
            return cache.get(key);
        }

        clearCache(key);
        return null;
    }

    /**
     * 判断缓存是否到期
     * @param key
     * @return
     */
    private static  boolean isExpire(String key){
        if(isExist(key)) {
            Long expireTime = cacheExpireTime.get(key);
            Long cacheStartTime = cacheTime.get(key);
            long nowTime = System.currentTimeMillis();
            if (expireTime == -1L || (nowTime - cacheStartTime) <= expireTime) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否存在该缓存
     * @return
     */
    public static boolean isExist(String key){
        if(cache.get(key) != null){
            return true;
        }
        return false;
    }

    /**
     * 清除单个缓存
     * @param key
     */
    public static void clearCache(String key){
       cache.remove(key);
       cacheExpireTime.remove(key);
       cacheTime.remove(key);
       cacheDate.remove(key);
    }

    /**
     * 清除所有缓存
     */
    public static void clearAllCache(){
        cache.clear();
        cacheExpireTime.clear();
        cacheTime.clear();
    }

}
