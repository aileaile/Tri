package com.LL.Triangle.pojo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lobby {
    private static Lobby singleton = new Lobby();
    public static Map<Integer,IRoom> roomMap = new ConcurrentHashMap<>();
    private Lobby(){
        //1-10号房间：三信
        for(int i=1 ; i<=10 ; i++){
            roomMap.put(i,new TriangleRoom(i));
        }
        //11-20号房间：别的

    }
    public static Lobby getInstance(){
        return singleton;
    }
    public IRoom getRoom(Integer roomNum){
        return roomMap.get(roomNum);
    }
}
