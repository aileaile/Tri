package com.LL.Triangle.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    @RequestMapping("/sit")
    @ResponseBody
    public String sit(String mydata,HttpServletRequest request){
        String s = request.getParameter("mydata");
        System.out.println(s);
        System.out.println(mydata);
        return "12344";
    }


}
