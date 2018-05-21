package com.LL.Triangle.pojo;

import com.LL.Triangle.pojo.po.UserPo;

public class User extends UserPo{

    public User(){}
    public User(String userId){
        this.userId = userId;
    }
    public User(String userId,String userName){
        this.userId = userId;
        this.userName = userName;
    }

    private String userId;
    private String userName;
    private boolean isReady = false;
    private long lastCheckTime = System.currentTimeMillis();

    public long getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(long lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String jSessionId) {
        this.userId = jSessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId != null ? userId.equals(user.userId) : user.userId == null;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
