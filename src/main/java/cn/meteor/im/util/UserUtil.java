package cn.meteor.im.util;

import cn.meteor.im.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meteor
 */
public class UserUtil {

    public static UserInfo getCurrentUser(HttpServletRequest request) {
        return (UserInfo) request.getAttribute("currentUser");
    }
}
