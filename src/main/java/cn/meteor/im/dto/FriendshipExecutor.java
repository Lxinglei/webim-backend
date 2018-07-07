package cn.meteor.im.dto;

import cn.meteor.im.entity.Friendship;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.FriendshipStateEnum;

import java.util.List;

/**
 * @author Meteor
 */
public class FriendshipExecutor extends BaseExecutor {

    private Friendship friendship;

    private List<Friendship> friendshipList;


    public FriendshipExecutor(FriendshipStateEnum stateEnum) {
        this.setState(stateEnum.getState());
        this.setMessage(stateEnum.getMessage());
    }

    public FriendshipExecutor(FriendshipStateEnum stateEnum, Friendship friendship) {
        this.setState(stateEnum.getState());
        this.setMessage(stateEnum.getMessage());
        this.setFriendship(friendship);
    }

    public FriendshipExecutor(FriendshipStateEnum stateEnum, List<Friendship> friendshipList) {
        this.setState(stateEnum.getState());
        this.setMessage(stateEnum.getMessage());
        this.setFriendshipList(friendshipList);
    }

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public List<Friendship> getFriendshipList() {
        return friendshipList;
    }

    public void setFriendshipList(List<Friendship> friendshipList) {
        this.friendshipList = friendshipList;
    }

}
