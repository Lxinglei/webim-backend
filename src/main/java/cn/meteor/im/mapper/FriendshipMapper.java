package cn.meteor.im.mapper;

import cn.meteor.im.entity.Friendship;
import cn.meteor.im.entity.UserInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Meteor
 */
public interface FriendshipMapper extends Mapper<Friendship> {

    /**
     * 查询好友列表
     * @param currentUser
     * @return
     */
    List<Friendship> selectFriends(UserInfo currentUser);
}
