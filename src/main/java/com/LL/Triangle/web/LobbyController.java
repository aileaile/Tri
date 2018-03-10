package com.LL.Triangle.web;

import com.LL.Triangle.pojo.IRoom;
import com.LL.Triangle.pojo.Lobby;
import com.LL.Triangle.pojo.TriangleRoom;
import com.LL.Triangle.pojo.User;
import com.LL.Triangle.utils.JsonUtil;
import com.LL.Triangle.webSocket.LobbyWebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    @RequestMapping("/sit")
    @ResponseBody
    public void sit(String data,HttpSession session)  {
        int roomNum = Integer.parseInt(JsonUtil.getNode(data, "room"));
        int seat = Integer.parseInt(JsonUtil.getNode(data, "seat"));
        User user = new User(session.getId(), JsonUtil.getNode(data, "userName"));
        if (1<=roomNum&&roomNum<=10){
            Lobby lobby = Lobby.getInstance();
            IRoom room = lobby.getRoom(roomNum);
            boolean flg = room.sit(seat, user);
            if (flg) {
                LobbyWebSocket.sendAll(room.getAll());
            }
        }
    }
}
