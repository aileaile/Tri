package com.LL.Triangle.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    //转向欢迎页面
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/test")
    @ResponseBody
    public void test(String optionsRadios){
        System.out.print(optionsRadios);
    }
}
