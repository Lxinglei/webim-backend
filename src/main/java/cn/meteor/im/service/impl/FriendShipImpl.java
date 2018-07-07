package cn.meteor.im.service.impl;

import cn.meteor.im.dto.FriendshipExecutor;
import cn.meteor.im.entity.Friendship;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.FriendshipStateEnum;
import cn.meteor.im.exceptions.FriendshipOperationException;
import cn.meteor.im.mapper.FriendshipMapper;
import cn.meteor.im.service.FriendShipService;
import cn.meteor.im.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Meteor
 */
@Service
public class FriendShipImpl implements FriendShipService {

    @Autowired
    private FriendshipMapper friendshipMapper;

    @Autowired
    private UserService userService;

    @Override
    public FriendshipExecutor listFriends(UserInfo currentUser) {

        try {
            Example friendshipExample = new Example(Friendship.class);
            Assert.notNull(currentUser.getUserId(), "userId不能为空");
            friendshipExample.createCriteria().andEqualTo("userId", currentUser.getUserId());

            List<Friendship> friendshipList = friendshipMapper.selectByExample(friendshipExample);

            friendshipList.stream().forEach(friendship -> {
                UserInfo friend = userService.findUserInfoByUserId(friendship.getFriendId());
                Assert.notNull(friend, "查询好友失败");
                friendship.setFriend(friend);
            });

            return new FriendshipExecutor(FriendshipStateEnum.SUCCESS, friendshipList);
        } catch (Exception e) {
            throw new FriendshipOperationException(e.getMessage());
        }
    }
}
