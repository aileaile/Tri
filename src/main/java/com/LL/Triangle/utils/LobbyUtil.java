package com.LL.Triangle.utils;

public class LobbyUtil {
    public static boolean checkRoomNum(Integer roomNum){
        if(1<=roomNum&&roomNum<=10){
            return true;
        }
        return false;
    }
}
