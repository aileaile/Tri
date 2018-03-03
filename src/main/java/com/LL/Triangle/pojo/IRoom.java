package com.LL.Triangle.pojo;

public interface IRoom{
    boolean sit(Integer pos,User user);
    boolean firstSit(User user);
    boolean leaveSeat(User user);
    String getAll();
    boolean isFull();

}
