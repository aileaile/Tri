package com.LL.Triangle.web;

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
    public String sit(String data,HttpSession session)  {
        int table = Integer.parseInt(JsonUtil.getNode(data, "table"));
        int seat = Integer.parseInt(JsonUtil.getNode(data, "seat"));
        User user = new User(session.getId(), JsonUtil.getNode(data, "userName"));
        if (table == 1)
        {
           /* boolean flg = TriangleRoom.sit(seat, user);
            if (flg) {
                LobbyWebSocket.broadcastMsg(TableOne.getAll());
            }*/
        }
        return "";
    }
}
