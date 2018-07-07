package cn.meteor.im.service;

import cn.meteor.im.dto.FriendshipExecutor;
import cn.meteor.im.entity.UserInfo; /**
 * @author Meteor
 */
public interface FriendShipService {

    /**
     * 获取好友列表
     * @param currentUser
     * @return
     */
    FriendshipExecutor listFriends(UserInfo currentUser);
}
