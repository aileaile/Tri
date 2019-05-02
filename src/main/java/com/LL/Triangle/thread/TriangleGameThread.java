package com.LL.Triangle.thread;

import com.LL.Triangle.pojo.TriangleGamePlayer;
import com.LL.Triangle.service.ITriangleGameService;
import com.LL.Triangle.utils.ContextUtil;
import com.LL.Triangle.utils.DictionaryUtil;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TriangleGameThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(TriangleGameThread.class);

    private List<String> aliveList;
    private List<String> undecidedList;
    private Map<String, TriangleGamePlayer> playerMap;
    private Integer roomNum;

    public void initData(List<String> aliveList,List<String> undecidedList,
                         Map<String, TriangleGamePlayer> playerMap,Integer roomNum) {
        this.aliveList = aliveList;
        this.undecidedList = undecidedList;
        this.playerMap = playerMap;
        this.roomNum = roomNum;
    }

    @Override
    public void run() {
        ITriangleGameService iTriangleGameService = (ITriangleGameService) ContextUtil.getBean("iTriangleGameService");
        int roundCount = 0;
        while(aliveList.size()>=2 && roundCount++<50){
            logger.debug("a new Round start.RoomNum-{}",roomNum);
            //每2秒检查一次是否所有玩家都提交，共5次，10秒
            for(int i = 0;i<10;i++){
                try {
                    Thread.sleep(2000);
                    logger.debug("Checking if all players have committed..RoomNum-{}",roomNum);
                    if(undecidedList.size()==0){break;}
                } catch (InterruptedException e) {
                    logger.warn("循环检测是否所有玩家都提交时出现InterruptedException：",e);
                }
            }
            logger.debug("Counting is over.RoomNum-{}",roomNum);
            //给10秒未做决策的玩家强制做决策
            TriangleGamePlayer player = null;
            logger.debug("Now making decisions for undecidedList.");
            for(String undecidedSession : undecidedList){
                player = playerMap.get(undecidedSession);
                player.makeDecision(DictionaryUtil.DEFENCE_1);
            }

            logger.debug("Now finding the final numbers of this round.");
            //本回合决策集合{atkNum,atkPow,manaAtk,manaGrab,recoverManaNum};
            Object[] roundInfo = {0,0,false,false,0};
            for(String aliveSession :aliveList){
                player = playerMap.get(aliveSession);
                player.useMana();
                Object[] pInfo = player.getInterplayInfo();
                //atkNum atkPow
                if((int)roundInfo[0] <= (int)pInfo[0]){
                    roundInfo[0] = pInfo[0];
                    roundInfo[1] = ((int)roundInfo[1]<(int)pInfo[1])?pInfo[1]:roundInfo[1];
                }
                //manaAtk
                roundInfo[2] = (boolean)roundInfo[2] || (boolean)pInfo[2];
                //manaGrab
                roundInfo[3] = (boolean)roundInfo[3] || (boolean)pInfo[3];
                //recoverManaNum
                roundInfo[4] = (int)roundInfo[4] + (int)pInfo[4];
            }
            logger.debug("Round info:atkNum-{},atkPow-{},manaAtk-{},manaGrab-{},recoverManaNum-{}.RoomNum-{}",
                                roundInfo[0],roundInfo[1],roundInfo[2],roundInfo[3],roundInfo[4],roomNum);
            //recoverMana、grabMana、defend
            if(!(boolean)roundInfo[2]&&!(boolean)roundInfo[3]){
                logger.debug("Start recoverMana.RoomNum is {}",roomNum);
                for(String aliveSession :aliveList){
                    player = playerMap.get(aliveSession);
                    player.recoverMana();
                }
            }
            if((boolean)roundInfo[3]){
                logger.debug("Start grabMana.RoomNum-{}",roomNum);
                for(String aliveSession :aliveList){
                    player = playerMap.get(aliveSession);
                    player.grabMana((int)roundInfo[4]);
                }
            }
            logger.debug("Start attack/defend.RoomNum-{}",roomNum);
            Iterator<String> it = aliveList.iterator();
            while(it.hasNext()){
                String aliveSession = it.next();
                player = playerMap.get(aliveSession);
                boolean ifPAlive = player.defend((int) roundInfo[0], (int) roundInfo[1], (boolean) roundInfo[2]);
                if (!ifPAlive) {
                    it.remove();
                }
            }
            logger.debug("One round is over,RoomNum-{}",roomNum);
            //-----sendToBrowser------
            undecidedList.clear();
            for(String aliveSession :aliveList){
                undecidedList.add(aliveSession);
            }
            String playerJson = iTriangleGameService.getPlayerMapJson(roomNum);
            LobbyWebSocket.sendRoom(roomNum,"{\"msgType\":\"gameStatus\",\"gameStatus\":["+playerJson+"]}");
            //------------------------
            LobbyWebSocket.broadcastRoom(roomNum,"回合结束(round:"+roundCount+")");
        }
        String playerJson = iTriangleGameService.getPlayerMapJson(roomNum);
        LobbyWebSocket.sendRoom(roomNum,"{\"msgType\":\"gameStatus\",\"gameStatus\":["+playerJson+"]}");
        if(roundCount<50) {
            if(aliveList.size()>0) {
                LobbyWebSocket.broadcastRoom(roomNum, "游戏结束，胜利者是：" + playerMap.get(aliveList.get(0)).getUserName());
            }else
                LobbyWebSocket.broadcastRoom(roomNum, "游戏结束，平局。");
        }else{
            LobbyWebSocket.broadcastRoom(roomNum, "超过最大回合数，平局。");
        }
        //清除playerMap数据。
        iTriangleGameService.cleanAfterGame(roomNum);
        LobbyWebSocket.sendRoom(roomNum,"{\"msgType\":\"gameOver\"}");
    }

}
