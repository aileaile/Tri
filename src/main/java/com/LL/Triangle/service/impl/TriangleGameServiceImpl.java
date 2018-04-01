package com.LL.Triangle.service.impl;

import com.LL.Triangle.pojo.IRoom;
import com.LL.Triangle.pojo.Lobby;
import com.LL.Triangle.pojo.TriangleGamePlayer;
import com.LL.Triangle.service.ITriangleGameService;
import com.LL.Triangle.thread.TriangleGameThread;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class TriangleGameServiceImpl implements ITriangleGameService {

    @Override
    public void gameStart(Integer roomNum) {
        IRoom gameRoom = Lobby.getInstance().getRoom(roomNum);
        Map<String, TriangleGamePlayer> playerMap = gameRoom.gameStartAndGetMap();
        List<String> aliveList = new LinkedList<>();
        for(TriangleGamePlayer player:playerMap.values()){
            aliveList.add(player.getjSessionId());
        }
        TriangleGameThread gameThread = new TriangleGameThread();
        gameThread.initData(aliveList,gameRoom.getUndecidedList(),playerMap,roomNum);
        Thread targetThread = new Thread(gameThread);
        targetThread.start();
    }
}
