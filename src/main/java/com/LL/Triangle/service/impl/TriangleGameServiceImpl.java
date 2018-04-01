package com.LL.Triangle.service.impl;

import com.LL.Triangle.pojo.TriangleGamePlayer;
import com.LL.Triangle.pojo.TriangleGameTable;
import com.LL.Triangle.service.ITriangleGameService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TriangleGameServiceImpl implements ITriangleGameService {

    private Map<Integer,Map<String,TriangleGamePlayer>> totalGameMap  = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
    }

    @Override
    public void gameStart(Integer roomNum) {
        Map<String, TriangleGamePlayer> gameMap = totalGameMap.get(roomNum);
        if(gameMap ==null) {
            Map<String,TriangleGamePlayer> newGanmeMap = new ConcurrentHashMap<>();
            totalGameMap.put(roomNum,newGanmeMap);
            gameMap = newGanmeMap;
        }
    }
}
