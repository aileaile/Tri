package com.LL.Triangle.pojo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一号桌
 * 三信
 * 8个位子
 */
public class TriangleRoom implements IRoom {
    public static Map<Integer,User> map = new ConcurrentHashMap<>();
    static{
    }

    /**
     * 坐下
     * @param pos 目标位置
     * @param user 用户
     * @return true：成功 false：失败（不变）
     */
    public static boolean sit(int pos,User user){
        synchronized(map){
            if(map.get(pos)!=null){
                return false;
            }
            leaveSeat(user);
            map.put(pos,user);
            return true;
        }
    }

    /**
     * 第一次入座
     */
    public static boolean firstSit(User user){
        synchronized(map){
            for(int i = 1; i<=8 ;i++){
                if(map.get(i)==null){
                    map.put(i,user);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 离开座位
     */
    public static boolean leaveSeat(User user){
        return map.values().remove(user);
    }

    /**
     * 所有位子情况
     */
    public static String getAll(){
        StringBuilder sb = new StringBuilder("\"msgType\":\"seatStatus\",");
        synchronized(map) {
            for (int i = 1; i <= 8; i++) {
                if (map.get(i) != null) {
                    sb.append("\"").append(i).append("\":\"").append(map.get(i).getUserName()).append("\",");
                }
            }
            if (!"".equals(sb.toString())) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return "{" + sb.toString() + "}";
        }
    }

}
