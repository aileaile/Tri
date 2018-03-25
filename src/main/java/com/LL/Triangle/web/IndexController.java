package com.LL.Triangle.web;

import com.LL.Triangle.pojo.User;
import com.LL.Triangle.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;



@Controller
public class IndexController {

    @Autowired
    private IUserService iUserService;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    //转向欢迎页面
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/chat")
    public String chat(String userName,String roomNum,HttpServletRequest request) throws UnsupportedEncodingException {
        String harmoniousName;
        //1.规范用户名称，储存用户登录信息
        if(userName!=null&&!"".equals(userName)) {
            //皮一下很开心
            harmoniousName = userName.trim().replace(" ", "").replace("ll", "I am ")
                    .replace("LL", "I am ").replace("刘良王", "赵赫王");
        }else{
            return "index";
        }
        logger.info("{}登陆了。",userName);
        request.getSession().setAttribute("userName",harmoniousName);
        //储存在线用户信息
        User user = new User(request.getSession().getId(), harmoniousName);
        iUserService.userSignIn(user);
        //2.处理房间号
        request.getSession().setAttribute("roomNum",roomNum);
        return "lobby";
    }

    @RequestMapping("/game")
    public String game(Model model, String userName, HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        //model.addAttribute("userName",userName);
        //equest.getSession(true);
        session.setAttribute("userName",session.getAttribute("userName"));
        return "insideDiv-game";
    }
}
