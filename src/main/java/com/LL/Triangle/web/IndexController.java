package com.LL.Triangle.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class IndexController {

    //转向欢迎页面
    @RequestMapping("/index")
    public String index(){
        System.out.println("ahaha");
        return "index";
    }

    @RequestMapping("/test")
    public String test(Model model, String userName, HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println(userName);
        model.addAttribute("userName",userName);
        return "chat";
    }
}
