package com.LL.Triangle.web;

import com.LL.Triangle.pojo.IRoom;
import com.LL.Triangle.pojo.Lobby;
import com.LL.Triangle.pojo.TriangleRoom;
import com.LL.Triangle.pojo.User;
import com.LL.Triangle.service.IUserService;
import com.LL.Triangle.service.impl.UserServiceImpl;
import com.LL.Triangle.utils.JsonUtil;
import com.LL.Triangle.utils.LobbyUtil;
import com.LL.Triangle.webSocket.LobbyWebSocket;
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

    @RequestMapping("/sit")
    @ResponseBody
    public void sit(String data,HttpSession session)  {
        int roomNum = Integer.parseInt(JsonUtil.getNode(data, "room"));
        int seat = Integer.parseInt(JsonUtil.getNode(data, "seat"));
        //User user = new User(session.getId(), JsonUtil.getNode(data, "userName"));
        if (LobbyUtil.checkRoomNum(roomNum)){
            Lobby lobby = Lobby.getInstance();
            IRoom room = lobby.getRoom(roomNum);
            User user = room.findUserBySessionId(session.getId());
            boolean flg = room.sit(seat, user);
            if (flg) {
                LobbyWebSocket.sendRoom(roomNum,room.getAll());
            }
        }
    }

    @RequestMapping("/firstSit")
    @ResponseBody
    public boolean firstSit(String roomNum,HttpSession session)  {
        try {
            Integer roomNumInt = Integer.valueOf(roomNum);
            User user = iUserService.findUser(session.getId());
            if (user != null) {
                IRoom room = Lobby.getInstance().getRoom(roomNumInt);
                boolean firstSit = room.firstSit(user);
                if(firstSit){
                    LobbyWebSocket.sendRoom(roomNumInt,room.getAll());
                    return true;
                }
            }
        }catch (Exception e){
            //log.error
        }
        return false;
    }

    @RequestMapping("/heartBeat")
    @ResponseBody
    public void heartBeat(HttpSession httpSession){
        iUserService.updateHeartBeat(httpSession.getId());
    }

    @RequestMapping("/ready")
    @ResponseBody
    public void ready(String roomNum,HttpSession session)  {
        try{
            Integer roomNumInt = Integer.valueOf(roomNum);
            String sessionId = session.getId();
            User user = iUserService.findUser(sessionId);
            if (user != null) {
                if(!user.isReady()) {//先判断用户是否还没有准备
                    user.setReady(true);
                    IRoom room = Lobby.getInstance().getRoom(roomNumInt);
                    room.findUserBySessionId(sessionId).setReady(true);
                    LobbyWebSocket.sendRoom(roomNumInt, room.getAll());
                    if(room.checkIfAllReady()){
                        if(room.getMap().size()>=2){
                            //游戏即将开始
                            LobbyWebSocket.broadcastRoom(roomNumInt,"所有玩家都已准备，游戏将在3秒内开始。");
                            LobbyWebSocket.sendRoom(roomNumInt,"{\"msgType\":\"gameStart\"");
                        }else {
                            //人数不足
                            LobbyWebSocket.broadcastRoom(roomNumInt,"当前人数不足，无法开始游戏。");
                        }
                    }
                }
            }
        }catch (Exception e){
           //log.err;
        }
    }

    @RequestMapping("/unReady")
    @ResponseBody
    public void unReady(String roomNum,HttpSession session)  {
        try{
            Integer roomNumInt = Integer.valueOf(roomNum);
            String sessionId = session.getId();
            User user = iUserService.findUser(sessionId);
            if (user != null) {
                if(user.isReady()) {//先判断用户是否准备
                    user.setReady(false);
                    IRoom room = Lobby.getInstance().getRoom(roomNumInt);
                    room.findUserBySessionId(sessionId).setReady(false);
                    LobbyWebSocket.sendRoom(roomNumInt, room.getAll());
                }
            }
        }catch (Exception e){
            //log.err;
        }
    }
}
