package com.LL.Triangle.service;

import com.LL.Triangle.pojo.User;
import com.LL.Triangle.pojo.po.UserPo;
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
     * 检查用户名是否重复
     * @param userName
     * @return true:无重复 false：重复
     */
    boolean checkLogin(String userName);

    /**
     * 更新心跳
     * @param sessionId
     */
    void updateHeartBeat(String sessionId);

    int deleteById(Integer id);

    int insert(UserPo record);

    UserPo selectById(Integer id);

    UserPo selectByName(String name);

    int updateById(UserPo record);

    UserPo findByNameAndPassword(String name,String password);
}
