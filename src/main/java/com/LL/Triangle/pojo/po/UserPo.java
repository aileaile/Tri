package com.LL.Triangle.pojo.po;

import java.io.Serializable;

public class UserPo implements Serializable {
    private Integer id;

    private String name;

    private String password;

    private String profilePicture;

    private String rmk1;

    private String rmk2;

    private String rmk3;

    private String rmk4;

    private String rmk5;

    private String rmk6;

    private String rmk7;

    private Integer rmk8;

    private Integer rmk9;

    private Integer rmk10;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture == null ? null : profilePicture.trim();
    }

    public String getRmk1() {
        return rmk1;
    }

    public void setRmk1(String rmk1) {
        this.rmk1 = rmk1 == null ? null : rmk1.trim();
    }

    public String getRmk2() {
        return rmk2;
    }

    public void setRmk2(String rmk2) {
        this.rmk2 = rmk2 == null ? null : rmk2.trim();
    }

    public String getRmk3() {
        return rmk3;
    }

    public void setRmk3(String rmk3) {
        this.rmk3 = rmk3 == null ? null : rmk3.trim();
    }

    public String getRmk4() {
        return rmk4;
    }

    public void setRmk4(String rmk4) {
        this.rmk4 = rmk4 == null ? null : rmk4.trim();
    }

    public String getRmk5() {
        return rmk5;
    }

    public void setRmk5(String rmk5) {
        this.rmk5 = rmk5 == null ? null : rmk5.trim();
    }

    public String getRmk6() {
        return rmk6;
    }

    public void setRmk6(String rmk6) {
        this.rmk6 = rmk6 == null ? null : rmk6.trim();
    }

    public String getRmk7() {
        return rmk7;
    }

    public void setRmk7(String rmk7) {
        this.rmk7 = rmk7 == null ? null : rmk7.trim();
    }

    public Integer getRmk8() {
        return rmk8;
    }

    public void setRmk8(Integer rmk8) {
        this.rmk8 = rmk8;
    }

    public Integer getRmk9() {
        return rmk9;
    }

    public void setRmk9(Integer rmk9) {
        this.rmk9 = rmk9;
    }

    public Integer getRmk10() {
        return rmk10;
    }

    public void setRmk10(Integer rmk10) {
        this.rmk10 = rmk10;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserPo other = (UserPo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UserPo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", rmk1='" + rmk1 + '\'' +
                ", rmk2='" + rmk2 + '\'' +
                ", rmk3='" + rmk3 + '\'' +
                ", rmk4='" + rmk4 + '\'' +
                ", rmk5='" + rmk5 + '\'' +
                ", rmk6='" + rmk6 + '\'' +
                ", rmk7='" + rmk7 + '\'' +
                ", rmk8=" + rmk8 +
                ", rmk9=" + rmk9 +
                ", rmk10=" + rmk10 +
                '}';
    }
}