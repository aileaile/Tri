package com.LL.Triangle.pojo;

import com.LL.Triangle.pojo.Triangle.TriangleRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lobby {
    private static Lobby singleton = new Lobby();
    private Logger logger = LoggerFactory.getLogger(Lobby.class);
    public static Map<Integer,IRoom> roomMap ;
    private Lobby(){
        logger.info("Lobby.class init");
        if(roomMap==null){
            roomMap = new ConcurrentHashMap<>();
        }
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
