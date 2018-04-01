package com.LL.Triangle.thread;

import com.LL.Triangle.pojo.TriangleGamePlayer;
import com.LL.Triangle.utils.DictionaryUtil;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        while(aliveList.size()>=2){
            //每2秒检查一次是否所有玩家都提交，共5次，10秒
            for(int i = 0;i<5;i++){
                try {
                    Thread.sleep(2000);
                    if(undecidedList.size()==0){break;}
                } catch (InterruptedException e) {
                    logger.error("循环检测是否所有玩家都提交时出错：",e);
                }
            }
            //给10秒未做决策的玩家强制做决策
            TriangleGamePlayer player = null;
            for(String undecidedSession : undecidedList){
                player = playerMap.get(undecidedSession);
                player.makeDecision(DictionaryUtil.DEFENCE_1);
            }
            //本回合决策集合{atkNum,atkPow,manaAtk,manaGrab,recoverManaNum};
            Object[] roundInfo = {0,0,false,false,0};
            for(String aliveSession :aliveList){
                player = playerMap.get(aliveSession);
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
            //recoverMana、grabMana、defend
            if(!(boolean)roundInfo[2]&&!(boolean)roundInfo[3]){
                for(String aliveSession :aliveList){
                    player = playerMap.get(aliveSession);
                    player.recoverMana();
                }
            }
            if((boolean)roundInfo[3]){
                for(String aliveSession :aliveList){
                    player = playerMap.get(aliveSession);
                    player.grabMana((int)roundInfo[4]);
                }
            }
            for(String aliveSession :aliveList) {
                player = playerMap.get(aliveSession);
                boolean ifPAlive = player.defend((int) roundInfo[0], (int) roundInfo[1], (boolean) roundInfo[2]);
                if (!ifPAlive) {
                    aliveList.remove(aliveSession);
                }
            }
            LobbyWebSocket.sendRoom(roomNum,"一回合结束了");
        }
        LobbyWebSocket.sendRoom(roomNum,"游戏结束，胜利者是："+playerMap.get(aliveList.get(0)).getUserName());
    }
}
