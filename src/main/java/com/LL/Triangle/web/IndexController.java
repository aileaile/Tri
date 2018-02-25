package com.LL.Triangle.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
public class IndexController {

    //转向欢迎页面
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/chat")
    public String chat(Model model, String userName, HttpServletRequest request) throws UnsupportedEncodingException {
        //model.addAttribute("userName",userName);
        request.getSession().setAttribute("userName",userName);
        return "lobby";
    }

    @RequestMapping("/game")
    public String game(Model model, String userName, HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        //model.addAttribute("userName",userName);
        //equest.getSession(true);
        session.setAttribute("userName",session.getAttribute("userName"));
        return "game";
    }
}
