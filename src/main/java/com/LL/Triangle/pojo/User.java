package com.LL.Triangle.pojo;

public class User {

    public User(){}
    public User(String jSessionId){
        this.jSessionId = jSessionId;
    }
    public User(String jSessionId,String userName){
        this.jSessionId = jSessionId;
        this.userName = userName;
    }

    private String jSessionId;
    private String userName;
    private boolean isReady = false;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
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

        return jSessionId != null ? jSessionId.equals(user.jSessionId) : user.jSessionId == null;
    }

    @Override
    public int hashCode() {
        return jSessionId != null ? jSessionId.hashCode() : 0;
    }
}
