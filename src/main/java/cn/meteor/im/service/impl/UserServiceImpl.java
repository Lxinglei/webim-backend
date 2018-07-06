package cn.meteor.im.service.impl;

import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.UserStateEnum;
import cn.meteor.im.exceptions.UserOperationException;
import cn.meteor.im.mapper.LocalAuthMapper;
import cn.meteor.im.mapper.UserInfoMapper;
import cn.meteor.im.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author Meteor
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private LocalAuthMapper localAuthMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserExecutor addLocalUser(LocalAuth localAuth) {

        try {
            // 1.验证用户是否已注册
            if (isLocalUserRegistered(localAuth)) {
                logger.error("用户创建失败：{}", "该用户命已被占用");
                return new UserExecutor(UserStateEnum.REGISTERED);
            }

            // 2.创建UserInfo
            UserInfo userInfo = createUserInfo();

            // 3.插入LocalAuth并和UserInfo绑定
            localAuth.setUserId(userInfo.getUserId());
            localAuth.setCreateTime(new Date());
            localAuth.setLastUpdateTime(new Date());
            int effectedNum = localAuthMapper.insert(localAuth);
            if (effectedNum <= 0) {
                logger.error("用户创建失败：{}", "插入认证信息失败");
                throw new UserOperationException("用户创建失败：%s", "插入认证信息失败");
            }

            return new UserExecutor(UserStateEnum.SUCCESS, userInfo);

        } catch (Exception e) {
            logger.error("用户创建失败：{}", e.getMessage());
            throw new UserOperationException("用户创建失败：%s", e.getMessage());
        }
    }


    /**
     * 创建新的用户信息
     * @return UserInfo
     */
    private UserInfo createUserInfo() {

        UserInfo userInfo = new UserInfo();
        userInfo.setBirthday(new Date());
        userInfo.setStatus(UserStateEnum.ENABLED.getState());
        userInfo.setGender(UserStateEnum.GENDER_SECRET.getState());
        userInfo.setCreateTime(new Date());
        userInfo.setLastUpdateTime(new Date());

        int effectedNum = userInfoMapper.insert(userInfo);

        if (effectedNum <= 0) {
            logger.error("用户创建失败：{}", "插入用户信息失败");
            throw new UserOperationException("用户创建失败：%s", "插入用户信息失败");
        }

        return userInfo;
    }


    @Override
    public boolean isLocalUserRegistered(LocalAuth localAuth) {
        Example example = new Example(LocalAuth.class);
        example.createCriteria().andEqualTo("username", localAuth.getUsername());
        LocalAuth localAuthInDb = localAuthMapper.selectOneByExample(example);
        if (null == localAuthInDb) {
            return false;
        }
        return true;
    }

}
