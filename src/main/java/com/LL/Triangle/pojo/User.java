package com.LL.Triangle.pojo;

public class User {

    private String jSessionId;
    private String userName;

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

}
