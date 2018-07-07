package cn.meteor.im.dto;

import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.UserStateEnum;

/**
 * @author Meteor
 */
public class UserExecutor extends BaseExecutor {

    private UserInfo userInfo;

    private JwtResult jwt;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public UserExecutor(UserStateEnum userStateEnum) {
        this.setState(userStateEnum.getState());
        this.setMessage(userStateEnum.getMessage());
    }

    public UserExecutor(UserStateEnum userStateEnum, UserInfo userInfo) {
        this.setState(userStateEnum.getState());
        this.setMessage(userStateEnum.getMessage());
        this.setUserInfo(userInfo);
    }

    public UserExecutor(UserStateEnum userStateEnum, JwtResult jwtResult) {
        this.setState(userStateEnum.getState());
        this.setMessage(userStateEnum.getMessage());
        this.setJwt(jwtResult);
    }

    public JwtResult getJwt() {
        return jwt;
    }

    public void setJwt(JwtResult jwt) {
        this.jwt = jwt;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
