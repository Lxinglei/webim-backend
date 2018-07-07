package cn.meteor.im.service.impl;

import cn.meteor.im.BaseTest;
import cn.meteor.im.dto.FriendshipExecutor;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.service.FriendShipService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class FriendShipImplTest extends BaseTest {

    @Autowired
    private FriendShipService friendShipService;

    @Test
    public void listFriends() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(13L);
        FriendshipExecutor executor = friendShipService.listFriends(userInfo);
        Assert.assertEquals(2, executor.getFriendshipList().size());
    }
}