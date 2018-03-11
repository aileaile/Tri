package com.LL.Triangle.service;

import com.LL.Triangle.pojo.User;
import org.springframework.scheduling.annotation.Scheduled;

public interface IUserService {
    User findUser(String httpSessionId);

    /**
     * 用户登录
     * @param user
     * @return
     */
    void userSignIn(User user);

    /**
     * 用户登出
     * @param sessionId
     */
    void userSignOut(String sessionId);

    /**
     * 定时清理离线用户
     */
    void cleanOfflineUser();

    /**
     * 检查登录
     * @param sessionId
     * @return
     */
    boolean checkLogin(String sessionId);

    /**
     * 更新心跳
     * @param sessionId
     */
    void updateHeartBeat(String sessionId);
}
