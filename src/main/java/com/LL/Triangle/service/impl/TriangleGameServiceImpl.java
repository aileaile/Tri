package com.LL.Triangle.service.impl;

import com.LL.Triangle.pojo.*;
import com.LL.Triangle.service.ITriangleGameService;
import com.LL.Triangle.thread.TriangleGameThread;
import com.LL.Triangle.utils.JsonUtil;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("iTriangleGameService")
public class TriangleGameServiceImpl implements ITriangleGameService {

    private Logger logger = LoggerFactory.getLogger(TriangleGameServiceImpl.class);

    @Override
    public void gameStart(Integer roomNum) {
        logger.info("gameStart,roomNum is {}",roomNum);
        //1--
        IRoom gameRoom = Lobby.getInstance().getRoom(roomNum);
            gameRoom.setInProcess(true);
        //2--
        Map<String, TriangleGamePlayer> playerMap = gameRoom.gameStartAndGetMap();
        List<String> aliveList = new LinkedList<>();
        for(TriangleGamePlayer player:playerMap.values()){
            aliveList.add(player.getjSessionId());
        }
        //3--
        logger.info("game data init over.Now init a thread.");
        TriangleGameThread gameThread = new TriangleGameThread();
        gameThread.initData(aliveList,gameRoom.getUndecidedList(),playerMap,roomNum);
        Thread targetThread = new Thread(gameThread);
        logger.info("Thread init over.Now start a thread.");
        targetThread.start();
        logger.info("Thread is started.");
    }

    @Override
    public String getPlayerMapJson(Integer roomNum){
        logger.debug("getPlayerMapJson[start],roomNum is {}",roomNum);
        if(roomNum==null){
            return "";
        }
        IRoom targetRoom = Lobby.getInstance().getRoom(roomNum);
        Map<String, TriangleGamePlayer> playerMap = targetRoom.getPlayerMap();
        StringBuilder sb = new StringBuilder();
        for(TriangleGamePlayer tgp : playerMap.values()){
            //if(tgp.isAlive()){
                TriangleGamePlayerForJson tgp4Json = new TriangleGamePlayerForJson(tgp);
                String playerJson = JsonUtil.Obj2String(tgp4Json);
                sb.append(playerJson);
                sb.append(",");
            //}else{
            //    sb.append("death");
            //}
        }
        String str = sb.toString();
        String res = str.substring(0, str.length()-1);
        logger.debug("getPlayerMapJson[over]");
        return res;
    }

    @Override
    public void makeDcs(int roomNum, String httpSessionId, String dcs) {
        logger.debug("makeDcs[start]");
        IRoom room = Lobby.getInstance().getRoom(roomNum);
        TriangleGamePlayer player = room.getPlayerMap().get(httpSessionId);
        player.makeDecision(dcs);
        room.getUndecidedList().remove(httpSessionId);
        //通知其他人已提交
        LobbyWebSocket.sendRoom(roomNum,"{\"msgType\":\"playerMakeDcs\",\"playerName\":\""+player.getUserName()+"\"}");
        logger.debug("makeDcs[over]");
    }

    @Override
    public void cleanAfterGame(Integer roomNum) {
        logger.debug("cleanAfterGame[start]");
        //1.清理playerMap
        IRoom room = Lobby.getInstance().getRoom(roomNum);
        room.getPlayerMap().clear();
        //2.设置room的InProcess状态
        room.setInProcess(false);
        LobbyWebSocket.sendRoom(roomNum,room.getAll());
        logger.debug("cleanAfterGame[end]");
    }

}
