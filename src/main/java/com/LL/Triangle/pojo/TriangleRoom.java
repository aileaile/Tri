package com.LL.Triangle.pojo;

import com.LL.Triangle.utils.JsonUtil;

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
    public Map<Integer,User> map = new ConcurrentHashMap<>();
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
        StringBuilder sb = new StringBuilder("\"msgType\":\"seatStatus\",");
        synchronized(map) {
            for (int i = 1; i <= 8; i++) {
                if (map.get(i) != null) {
                    //sb.append("\"").append(i).append("\":\"").append(map.get(i).getUserName()).append("\",");
                    String userJson = JsonUtil.Obj2String(map.get(i));
                    sb.append(userJson);
                }
            }
            if (!"".equals(sb.toString())) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return "{" + sb.toString() + "}";
        }
    }


}
