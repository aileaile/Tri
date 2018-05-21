package com.LL.Triangle.test;

import com.LL.Triangle.pojo.po.UserPo;
import com.LL.Triangle.service.IUserService;
import com.LL.Triangle.utils.ContextUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitTest {

    private Logger logger = LoggerFactory.getLogger(UnitTest.class);

    @Test
    public void Test(){
        IUserService iUserService = (IUserService)ContextUtil.getBean("iUserService");
        UserPo userPo = iUserService.selectById(1);
        System.out.println(userPo);
    }

}
