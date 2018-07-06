package cn.meteor.im.service;

import cn.meteor.im.dto.UserExecutor;
import cn.meteor.im.entity.LocalAuth;

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
     * 判断该本地用户是否已注册
     * @param localAuth
     * @return
     */
    boolean isLocalUserRegistered(LocalAuth localAuth);

}
