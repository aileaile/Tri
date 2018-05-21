package com.LL.Triangle.web;

import com.LL.Triangle.pojo.po.UserPo;
import com.LL.Triangle.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @ResponseBody
    @RequestMapping("/signUp")
    public Integer signUp(HttpServletRequest request,String username,String password){
        logger.info("signUp,username:{}",username);
        UserPo up = iUserService.selectByName(username);
        if(up!=null){
            logger.info("signUp,username:{},username is occupied.",username);
            return 1;//1：用户名已被占用。
        }
        up = new UserPo();
        up.setName(username);
        up.setPassword(password);
        iUserService.insert(up);
        up = iUserService.selectByName(username);
        request.getSession().setAttribute("session_user",up);
        logger.info("signUp,username:{},success and signIn.",username);
        return 0;//0:SUCCESS.
    }

    @ResponseBody
    @RequestMapping("/signIn")
    public boolean signIn(HttpServletRequest request,String username,String password){
        logger.info("signIn,username:{}",username);
        UserPo up = iUserService.findByNameAndPassword(username, password);
        if(up==null){
            logger.info("signIn,username:{},fail to check user in database.",username);
            return false;
        }
        request.getSession().setAttribute("session_user",up);
        logger.info("signIn,username:{},success.",username);
        return true;
    }


    @RequestMapping("/signOut")
    public String signOut(HttpServletRequest request){
        try {
            logger.info("signOut,username:{}.", ((UserPo) request.getSession().getAttribute("session_user")).getName());
        }catch (Exception e){
            logger.error("signOut时记录日志时出错了：",e);
        }
        request.getSession().setAttribute("session_user",null);
        return "redirect:/index";
    }
}
