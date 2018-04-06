package com.LL.Triangle.web;

import com.LL.Triangle.service.ITriangleGameService;
import com.LL.Triangle.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/triGame")
public class TriangleGameController {

    private Logger logger = LoggerFactory.getLogger(TriangleGameController.class);

    @Autowired
    private ITriangleGameService iTriangleGameService;

    @RequestMapping("/makeDcs")
    @ResponseBody
    public void makeDcs(String data,HttpSession session)  {
        logger.debug("dec[start]");
        int roomNum = Integer.parseInt(JsonUtil.getNode(data, "room"));
        String dcs = JsonUtil.getNode(data, "decision");
        String httpSessionId = session.getId();
        iTriangleGameService.makeDcs(roomNum,httpSessionId,dcs);
        logger.debug("dec[end]");
    }
}
