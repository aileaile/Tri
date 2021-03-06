package com.LL.Triangle.pojo;

import java.util.List;
import java.util.Map;

public interface IRoom{
    boolean sit(Integer pos,User user);
    boolean firstSit(User user);
    boolean leaveSeat(User user);
    String getAll();
    boolean isFull();
    void setInProcess(boolean b);
    Map<Integer, User> getMap();
    User findUserBySessionId(String sessionId);
    boolean checkIfAllReady();

    Map<String,TriangleGamePlayer> gameStartAndGetMap();

    Map<String,TriangleGamePlayer> getPlayerMap();

    List<String> getUndecidedList();
}
