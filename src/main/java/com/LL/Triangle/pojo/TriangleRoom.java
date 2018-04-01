package com.LL.Triangle.pojo;

import com.LL.Triangle.utils.JsonUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 三信房间
 * 8个位子
 */
public class TriangleRoom implements IRoom {

    public TriangleRoom(int roomNum){
        this.roomNum = roomNum;
    }
    private int roomNum;
    private boolean inProcess = false;
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
        synchronized(map){
            if(map.get(pos)!=null){
                return false;
            }
            leaveSeat(user);
            map.put(pos,user);
            return true;
        }
    }

    /**
     * 第一次入座(若返回false表明房间已满)
     */
    @Override
    public boolean firstSit(User user){
        synchronized(map){
            for(int i = 1; i<=8 ;i++){
                if(map.get(i)==null){
                    map.put(i,user);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 检查房间是否已满
     * @return
     */
    @Override
    public boolean isFull() {
        synchronized(map){
            for(int i = 1; i<=8 ;i++){
                if(map.get(i)==null){
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 离开座位
     */
    @Override
    public boolean leaveSeat(User user){
        return map.values().remove(user);
    }

    /**
     * 获取所有位子情况
     */
    @Override
    public String getAll(){
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
            return "{" + sb.toString() + "]}";
        }
    }

    @Override
    public User findUserBySessionId(String sessionId){
        for(User user : map.values()){
            if(user.getjSessionId().equals(sessionId)){
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean checkIfAllReady(){
        for(User user : map.values()){
            if(!user.isReady()){
                return false;
            }
        }
        return true;
    }

    /**
     * 开始游戏初始化游戏玩家名单
     * @return
     */
    @Override
    public Map<String,TriangleGamePlayer> gameStartAndGetMap(){
        if(!inProcess){
            inProcess=true;
            TriangleGamePlayer tPlayer = null;
            for(User u :map.values()){
                tPlayer = new TriangleGamePlayer();
                tPlayer.setjSessionId(u.getjSessionId());
                tPlayer.setUserName(u.getUserName());
                tPlayer.reset();
                playerMap.put(u.getjSessionId(),tPlayer);
                undecidedList.add(u.getjSessionId());
            }
        }
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
