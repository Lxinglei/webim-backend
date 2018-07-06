package cn.meteor.im.service.impl;

import cn.meteor.im.BaseTest;
import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceImplTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void addLocalUser() {

        LocalAuth localAuth = new LocalAuth();
        localAuth.setUsername("duanqiyan");
        localAuth.setPassword("123456");

        try {
            UserExecutor userExecutor = userService.addLocalUser(localAuth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}