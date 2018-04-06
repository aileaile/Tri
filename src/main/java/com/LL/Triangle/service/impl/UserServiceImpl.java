package com.LL.Triangle.service.impl;

import com.LL.Triangle.pojo.Lobby;
import com.LL.Triangle.pojo.User;
import com.LL.Triangle.service.IUserService;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    private Map<String,User> userMap = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 查找用户
     * @param httpSessionId
     */
    @Override
    public User findUser(String httpSessionId){
        return this.userMap.get(httpSessionId);
    }

    /**
     * 用户登录
     * @param user
     */
    @Override
    public void userSignIn(User user) {
        logger.debug("userSignIn[start]");
        userMap.put(user.getjSessionId(),user);
        logger.debug("userSignIn[end]");
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
                boolean isLeave = Lobby.roomMap.get(i).leaveSeat(new User(sessionId));
                if(isLeave){
                    String json = Lobby.roomMap.get(i).getAll();
                    LobbyWebSocket.sendRoom(i,json);
                    break;
                }
            }
        }
    }

    /**
     * 定期检查断线用户
     */
    @Scheduled(cron = "*/30 * * * * ?")
    @Override
    public void cleanOfflineUser(){
        try {
            Iterator<Map.Entry<String, User>> it = userMap.entrySet().iterator();
            long curTime = System.currentTimeMillis();
            //确定刷新的房间号
            List<Integer> roomForUpdateList = new LinkedList<>();
            while (it.hasNext()) {
                User tempUser = it.next().getValue();
                //30秒没有收到心跳，则认为已经断线（心跳每9秒发送一次）
                if (curTime - tempUser.getLastCheckTime() > 30000) {
                    //1.删除房间roomMap中的user,遍历Lobby中所有的Room，i:房间号
                    for (int i = 1; i <= Lobby.roomMap.size(); i++) {
                        if (Lobby.roomMap.get(i).getMap().size() > 0) {
                            boolean userIsLeave = Lobby.roomMap.get(i).leaveSeat(tempUser);
                            if (userIsLeave) {
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
            if (roomForUpdateList.size() > 0) {
                for (Integer i : roomForUpdateList) {
                    String json = Lobby.roomMap.get(i).getAll();
                    LobbyWebSocket.sendRoom(i, json);
                }
            }
        }catch (Exception e){
            logger.error("检查离线用户时出现错误，详情：",e);
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

    /**
     * 更新心跳
     * @param sessionId
     */
    @Override
    public void updateHeartBeat(String sessionId) {
        userMap.get(sessionId).setLastCheckTime(System.currentTimeMillis());
    }
}
