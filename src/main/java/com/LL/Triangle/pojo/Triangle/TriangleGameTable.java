package com.LL.Triangle.pojo.Triangle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TriangleGameTable {
    private Integer roomNum;
    private Map<String,TriangleGamePlayer> gameMap = new ConcurrentHashMap<>();

    public TriangleGameTable(Integer roomNum){
        this.roomNum = roomNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Map<String, TriangleGamePlayer> getGameMap() {
        return gameMap;
    }

    public void setGameMap(Map<String, TriangleGamePlayer> gameMap) {
        this.gameMap = gameMap;
    }
}
