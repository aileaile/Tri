package com.LL.Triangle.web;

import com.LL.Triangle.pojo.User;
import com.LL.Triangle.pojo.po.UserPo;
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

    //转向欢迎页面
    @RequestMapping("/offLine")
    public String offLine(HttpServletRequest request){
        request.setAttribute("errMsg","Opps，断线了。");
        return "index";
    }



    @RequestMapping("/main")
    public String main(String roomNum,HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("1");
        UserPo session_user = (UserPo)request.getSession().getAttribute("session_user");
        if(session_user==null){
            logger.info("User did not sign in.");
            return "index";
        }
        logger.info("User joins the lobby.");
        //储存在线用户信息
        User user = new User(String.valueOf(session_user.getId()), session_user.getName());
        //这里把User存缓存
        iUserService.userSignIn(user);
        //2.处理房间号
        request.getSession().setAttribute("roomNum",roomNum);
        return "lobby";
    }

    @RequestMapping("/game")
    public String game(Model model, String userName, HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        //model.addAttribute("userName",userName);
        //request.getSession(true);
        session.setAttribute("userName",session.getAttribute("userName"));
        return "insideDiv-game";
    }


}
