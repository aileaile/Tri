package com.LL.Triangle.pojo.Triangle;

import com.LL.Triangle.pojo.IRoom;
import com.LL.Triangle.pojo.User;
import com.LL.Triangle.pojo.UserForJson;
import com.LL.Triangle.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 三信房间
 * 8个位子
 */
public class TriangleRoom implements IRoom {

    private Logger logger = LoggerFactory.getLogger(TriangleRoom.class);
    public TriangleRoom(int roomNum){
        this.roomNum = roomNum;
    }
    private int roomNum;
    private boolean inProcess = false;
    //map:key-座位号 value-User
    private Map<Integer,User> map = new ConcurrentHashMap<>();
    private Map<String,TriangleGamePlayer> playerMap = new ConcurrentHashMap<>();
    private List<String> undecidedList = new LinkedList<>();
    static{
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public Map<Integer, User> getMap() {
        return map;
    }

    public void setMap(Map<Integer, User> map) {
        this.map = map;
    }

    /**
     * 坐下
     * @param pos 目标位置
     * @param user 用户
     * @return true：成功 false：失败（不变）
     */
    @Override
    public boolean sit(Integer pos,User user){
        logger.debug("sit[start]:roomNum-{},position-{},username-{}",roomNum,pos,user.getUserName());
        synchronized(map){
            if(map.get(pos)!=null){
                logger.debug("sit[end] FAIL:the target position is occupied!The user on the seat is {},jSessionId is {}"
                                                        ,map.get(pos).getUserName(),map.get(pos).getUserId());
                return false;
            }
            leaveSeat(user);
            map.put(pos,user);
            logger.debug("sit[end] SUCCESS");
            return true;
        }
    }

    /**
     * 第一次入座(若返回false表明房间已满)
     */
    @Override
    public boolean firstSit(User user){
        logger.debug("firstSit[start]:roomNum-{},username-{}",roomNum,user.getUserName());
        synchronized(map){
            for(int i = 1; i<=8 ;i++){
                if(map.get(i)==null){
                    map.put(i,user);
                    logger.debug("firstSit[end] SUCCESS:seat number-{}",i);
                    return true;
                }
            }
            logger.debug("firstSit[end] FAIL:The room is full(roomNum-{}).",roomNum);
            return false;
        }
    }

    /**
     * 检查房间是否已满
     * @return true:房间座位已满  false：未满
     */
    @Override
    public boolean isFull() {
        logger.debug("isFull[start]");
        synchronized(map){
            for(int i = 1; i<=8 ;i++){
                if(map.get(i)==null){
                    logger.debug("isFull[end]:Room is full,room number-{}",roomNum);
                    return true;
                }
            }
            logger.debug("isFull[end]:Room is not full,room number-{}",roomNum);
            return false;
        }
    }

    /**
     * 离开座位
     */
    @Override
    public boolean leaveSeat(User user){
        logger.debug("user leaves original seat.(username:{})",user.getUserName());
        return map.values().remove(user);
    }

    /**
     * 获取所有位子情况
     */
    @Override
    public String getAll(){
        logger.debug("getAll[start]");
        StringBuilder sb = new StringBuilder("\"msgType\":\"seatStatus\",\"roomNum\":\""+roomNum+"\",\"detail\":[");
        synchronized(map) {
            for (int i = 1; i <= 8; i++) {
                if (map.get(i) != null) {
                    //sb.append("\"").append(i).append("\":\"").append(map.get(i).getUserName()).append("\",");
                    //2018.03.10 17:27 下面的方法调用了UserForJson的构造方法。
                    String userJson = JsonUtil.Obj2String(new UserForJson(map.get(i),i));
                    sb.append(userJson).append(",");
                }
            }
            if (!"".equals(sb.toString())) {
                sb.deleteCharAt(sb.length() - 1);
            }
            logger.debug("getAll[end]");
            return "{" + sb.toString() + "]}";
        }
    }

    @Override
    public User findUserBySessionId(String sessionId){
        for(User user : map.values()){
            if(user.getUserId().equals(sessionId)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean checkIfAllReady(){
        logger.debug("checkIfAllReady[start]");
        for(User user : map.values()){
            if(!user.isReady()){
                logger.debug("checkIfAllReady[end]:not all ready");
                return false;
            }
        }
        logger.debug("checkIfAllReady[end]:all is ready");
        return true;
    }

    /**
     * 开始游戏初始化游戏玩家名单
     * @return
     */
    @Override
    public Map<String,TriangleGamePlayer> gameStartAndGetMap(){
        logger.debug("gameStartAndGetMap[start]");
        //if(!inProcess){
        //先清理
        playerMap.clear();
        undecidedList.clear();
        for(User user:map.values()){
            user.setReady(false);
        }
        //再开始
            inProcess=true;
            TriangleGamePlayer tPlayer = null;
            logger.info("game starting:roomNum-{},now setting the playerMap.Players number is {}",roomNum,map.size());
            for(User u :map.values()){
                tPlayer = new TriangleGamePlayer();
                tPlayer.setUserId(u.getUserId());
                tPlayer.setUserName(u.getUserName());
                tPlayer.reset();
                playerMap.put(u.getUserId(),tPlayer);
                undecidedList.add(u.getUserId());
                logger.info("player name-{},sessionId-{},done.",u.getUserName(),u.getUserId());
            }
        //}
        logger.debug("gameStartAndGetMap[end]");
        return playerMap;
    }

    /**
     * 获取玩家名单
     * @return playerMap
     */
    @Override
    public Map<String,TriangleGamePlayer> getPlayerMap(){
        return playerMap;
    }

    /**
     * 获取未决定名单，需要在gameStartAndGetMap方法后执行
     * @return
     */
    @Override
    public List<String> getUndecidedList(){
        return undecidedList;
    }

}
