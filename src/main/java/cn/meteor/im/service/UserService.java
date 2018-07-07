package cn.meteor.im.service;

import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.entity.UserInfo;

/**
 * @author Meteor
 */
public interface UserService {

    /**
     * 添加本地用户
     * @param localAuth
     * @return
     */
    UserExecutor addLocalUser(LocalAuth localAuth);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    LocalAuth findByUsername(String username);

    /**
     * 登录
     * @param localAuth
     * @return
     */
    UserExecutor loginLocalUser(LocalAuth localAuth);

    /**
     * 根据user_id获取UserInfo
     * @param friendId
     * @return
     */
    UserInfo findUserInfoByUserId(Long friendId);
}
