package com.LL.Triangle.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/lobby")
public class lobbyController {

    @RequestMapping("/sit")
    @ResponseBody
    public String sit(String mydata,HttpServletRequest request){
        String s = request.getParameter("mydata");
        System.out.println(s);
        System.out.println(mydata);
        return "12344";
    }


}
