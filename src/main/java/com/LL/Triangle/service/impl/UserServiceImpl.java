package com.LL.Triangle.service.impl;

import com.LL.Triangle.pojo.Lobby;
import com.LL.Triangle.pojo.User;
import com.LL.Triangle.service.IUserService;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements IUserService {
    private Map<String,User> userMap = new ConcurrentHashMap<>();

    /**
     * 用户登录
     * @param user
     */
    @Override
    public void userSignIn(User user) {
        userMap.put(user.getjSessionId(),user);
    }

    /**
     * 用户登出
     * @param sessionId
     */
    @Override
    public void userSignOut(String sessionId) {
        //1.删除在线用户userMap中的user
        userMap.remove(sessionId);
        //2.删除webSocket中的session
        LobbyWebSocket.sessionMap.remove(sessionId);
        //3.删除房间roomMap中的user,遍历Lobby中所有的Room，i:房间号
        for(int i=1;i<= Lobby.roomMap.size();i++){
            if(Lobby.roomMap.get(i).getMap().size()>0){
                Lobby.roomMap.get(i).getMap().remove(sessionId);
            }
        }
    }

    /**
     * 定期检查断线用户
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void cleanOfflineUser(){
        Iterator<Map.Entry<String,User>> it = userMap.entrySet().iterator();
        long curTime = System.currentTimeMillis();
        //确定刷新的房间号
        List<Integer> roomForUpdateList = new LinkedList<>();
        while(it.hasNext()){
            User tempUser = it.next().getValue();
            //30秒没有收到心跳，则认为已经断线（心跳每9秒发送一次）
            if(curTime-tempUser.getLastCheckTime()>30000){
                //1.删除房间roomMap中的user,遍历Lobby中所有的Room，i:房间号
                for(int i=1;i<= Lobby.roomMap.size();i++){
                    if(Lobby.roomMap.get(i).getMap().size()>0){
                        boolean userIsLeave = Lobby.roomMap.get(i).leaveSeat(tempUser);
                        if(userIsLeave){
                            roomForUpdateList.add(i);
                            break;
                        }
                    }
                }
                //2.删除webSocket中的session
                String httpSessionId = tempUser.getjSessionId();
                LobbyWebSocket.sessionMap.remove(httpSessionId);
                //3.删除在线用户userMap中的user
                userMap.remove(httpSessionId);
            }
        }
        if(roomForUpdateList.size()>0){
            //TODO 更新(webSocket 发送）
        }
    }
    /**
     * 检查用户是否已经登陆
     * @param sessionId
     * @return
     */
    @Override
    public boolean checkLogin(String sessionId) {
        return false;
    }
}
