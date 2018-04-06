package com.LL.Triangle.service;

public interface ITriangleGameService {
    /**
     * 游戏开始
     * @param roomNum
     */
    void gameStart(Integer roomNum);

    String getPlayerMapJson(Integer roomNumInt);

    void makeDcs(int roomNum, String httpSessionId, String dcs);
}
