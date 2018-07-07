package cn.meteor.im.service.impl;

import cn.meteor.im.dto.JwtResult;
import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.UserStateEnum;
import cn.meteor.im.exceptions.UserOperationException;
import cn.meteor.im.mapper.LocalAuthMapper;
import cn.meteor.im.mapper.UserInfoMapper;
import cn.meteor.im.service.UserService;
import cn.meteor.im.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
            if (null != findByUsername(localAuth.getUsername())) {
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
    public LocalAuth findByUsername(String username) {
        Assert.notNull(username, "用户名不能为空");
        Example example = new Example(LocalAuth.class);
        example.createCriteria().andEqualTo("username", username);
        return localAuthMapper.selectOneByExample(example);
    }

    @Override
    public UserExecutor loginLocalUser(LocalAuth localAuth) {

        try {
            LocalAuth localAuthInDb = findByUsername(localAuth.getUsername());

            if (null == localAuthInDb) {
                logger.error("查询用户失败：{}", localAuth.getUsername());
                throw new UserOperationException(UserStateEnum.LOGIN_FAILED.getMessage());
            }

            if (!localAuthInDb.getPassword().equals(localAuth.getPassword())) {
                logger.error("密码错误：{}, {}", localAuth.getUsername(), localAuth.getPassword());
                throw new UserOperationException(UserStateEnum.LOGIN_FAILED.getMessage());
            }

            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(localAuthInDb.getUserId());

            Claims claims = new DefaultClaims();
            claims.setId(Long.toString(userInfo.getUserId()));
            claims.put("userId", userInfo.getUserId());
            JwtResult jwt = JwtUtil.createJwt(claims, 10 * 60);

            return new UserExecutor(UserStateEnum.SUCCESS, jwt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserOperationException(e.getMessage());
        }
    }

}
