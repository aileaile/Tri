package com.LL.Triangle.pojo;

public class UserForJson {
    public UserForJson(){

    }
    public UserForJson(User user,Integer position){
        this.isReady = user.isReady();
        this.userName = user.getUserName();
        this.position = position;
    }
    private String userName;
    private boolean isReady;
    private Integer position;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
