package com.LL.Triangle.service;

import com.LL.Triangle.pojo.User;

public interface IUserService {
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
     * 检查登录
     * @param sessionId
     * @return
     */
    boolean checkLogin(String sessionId);
}
