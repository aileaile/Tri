package com.LL.Triangle.pojo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 一号桌
 * 三信
 * 8个位子
 */
public class TableOne {
    public static List<User> list = new CopyOnWriteArrayList<User>();
    static{
        list.add(0,null);
        list.add(1,null);
        list.add(2,null);
        list.add(3,null);
        list.add(4,null);
        list.add(5,null);
        list.add(6,null);
        list.add(7,null);
    }

    /**
     * 坐下
     * @param pos 目标位置
     * @param user 用户
     * @return true：成功 false：失败（不变）
     */
    public static boolean sit(int pos,User user){
        synchronized(list){
            if(list.get(pos)!=null){
                return false;
            }
            leaveSeat(user);
            list.add(pos,user);
            return true;
        }
    }

    /**
     * 第一次入座
     */
    public static boolean firstSit(User user){
        synchronized(list){
            for(int i = 0; i<8 ;i++){
                if(list.get(i)==null){
                    list.add(i,user);
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
        for(User u : list){
            if(u!=null&&u.equals(user)){
                list.remove(u);
                return true;
            }
        }
        return false;
    }
}
