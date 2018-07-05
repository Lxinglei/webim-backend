package cn.meteor.im.mapper;

import cn.meteor.im.BaseTest;
import cn.meteor.im.entity.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

public class UserInfoMapperTest extends BaseTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void insert() {

        UserInfo userInfo = new UserInfo();
        userInfo.setBirthday(new Date());
        userInfo.setCreateTime(new Date());
        userInfo.setGender(1);
        userInfo.setStatus(1);
        userInfo.setLastUpdateTime(new Date());
        userInfo.setSignature("上善若水");
        userInfo.setNickName("Meteor");
        int count = userInfoMapper.insert(userInfo);
        Assert.assertEquals(1, count);
        Assert.assertEquals(Long.valueOf(12L), userInfo.getUserId());
    }
}