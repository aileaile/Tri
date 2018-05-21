package com.LL.Triangle.web;

import com.LL.Triangle.pojo.IRoom;
import com.LL.Triangle.pojo.Lobby;
import com.LL.Triangle.pojo.User;
import com.LL.Triangle.pojo.po.UserPo;
import com.LL.Triangle.service.ITriangleGameService;
import com.LL.Triangle.service.IUserService;
import com.LL.Triangle.utils.JsonUtil;
import com.LL.Triangle.utils.LobbyUtil;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ITriangleGameService iTriangleGameService;

    private Logger logger = LoggerFactory.getLogger(LobbyController.class);


    @RequestMapping("/sit")
    @ResponseBody
    public void sit(String data,HttpSession session)  {
        logger.debug("sit[start]");
        int roomNum = Integer.parseInt(JsonUtil.getNode(data, "room"));
        int seat = Integer.parseInt(JsonUtil.getNode(data, "seat"));
        //User user = new User(session.getId(), JsonUtil.getNode(data, "userName"));
        logger.debug("user sit: roomNum-{},seat-{},sessionId-{}",roomNum,seat,session.getId());
        logger.debug("now start checking the roomNum(roomNum must between 1 and 10)");
        if (LobbyUtil.checkRoomNum(roomNum)){
            Lobby lobby = Lobby.getInstance();
            IRoom room = lobby.getRoom(roomNum);
            User user = room.findUserBySessionId(((UserPo)session.getAttribute("session_user")).getId().toString());
            boolean flg = room.sit(seat, user);
            if (flg) {
                LobbyWebSocket.sendRoom(roomNum,room.getAll());
            }
        }
        logger.debug("sit[end]");
    }

    @RequestMapping("/firstSit")
    @ResponseBody
    public boolean firstSit(String roomNum,HttpSession session)  {
        logger.info("firstSit[start]");
        User user = null;
        try {
            Integer roomNumInt = Integer.valueOf(roomNum);
            user = iUserService.findUser(((UserPo)session.getAttribute("session_user")).getId().toString());
            if (user != null) {
                IRoom room = Lobby.getInstance().getRoom(roomNumInt);
                boolean firstSit = room.firstSit(user);
                logger.info("firstSit[end]:SUCCESS");
                if(firstSit){
                    logger.debug("firstSit successfully, now sending room json data to browser");
                    LobbyWebSocket.sendRoom(roomNumInt,room.getAll());
                    return true;
                }
            }
        }catch (Exception e){
            logger.error("初次Sit时发生错误，UserName：{}，详情：",user.getUserName(),e);
        }
        logger.info("firstSit[end]:FAIL");
        return false;
    }

    @RequestMapping("/heartBeat")
    @ResponseBody
    public void heartBeat(HttpSession httpSession){
        iUserService.updateHeartBeat(((UserPo)httpSession.getAttribute("session_user")).getId().toString());
    }

    @RequestMapping("/ready")
    @ResponseBody
    public void ready(String roomNum,HttpSession session) {
        User user = null;
        try {
            Integer roomNumInt = Integer.valueOf(roomNum);
            String sessionId = session.getId();
            user = iUserService.findUser(sessionId);
            if (user != null) {
                if (!user.isReady()) {//先判断用户是否还没有准备
                    user.setReady(true);
                    IRoom room = Lobby.getInstance().getRoom(roomNumInt);
                    room.findUserBySessionId(sessionId).setReady(true);
                    LobbyWebSocket.sendRoom(roomNumInt, room.getAll());
                    if (room.checkIfAllReady()) {
                        if (room.getMap().size() >= 2) {
                            //游戏即将开始
                            room.setInProcess(true);
                            LobbyWebSocket.broadcastRoom(roomNumInt, "所有玩家都已准备，游戏将在3秒内开始。");
                            LobbyWebSocket.sendRoom(roomNumInt, "{\"msgType\":\"gameStart\"}");
                            iTriangleGameService.gameStart(roomNumInt);
                            String playMapJson = iTriangleGameService.getPlayerMapJson(roomNumInt);
                            logger.debug("{}",playMapJson);
                            logger.debug("{\"msgType\":\"playerStatus\",\"playerStatus\":["+playMapJson+"]}");
                            LobbyWebSocket.sendRoom(roomNumInt, "{\"msgType\":\"playerStatus\",\"playerStatus\":["+playMapJson+"]}");
                        } else {
                            //人数不足
                            LobbyWebSocket.broadcastRoom(roomNumInt, "当前人数不足，无法开始游戏。");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Ready时发生错误，UserName：{}，详情：", user.getUserName(), e);
        }
    }

    @RequestMapping("/unReady")
    @ResponseBody
    public void unReady(String roomNum,HttpSession session) {
        User user = null;
        try {
            Integer roomNumInt = Integer.valueOf(roomNum);
            String sessionId = session.getId();
            user = iUserService.findUser(sessionId);
            if (user != null) {
                if (user.isReady()) {//先判断用户是否准备
                    user.setReady(false);
                    IRoom room = Lobby.getInstance().getRoom(roomNumInt);
                    room.findUserBySessionId(sessionId).setReady(false);
                    LobbyWebSocket.sendRoom(roomNumInt, room.getAll());
                }
            }
        } catch (Exception e) {
            logger.error("unReady时发生错误，UserName：{}，详情：", user.getUserName(), e);
        }
    }
}
