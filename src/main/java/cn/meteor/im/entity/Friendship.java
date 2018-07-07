package cn.meteor.im.entity;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;

/**
 * 好友关系信息
 * @author Meteor
 */

@Table(name = "t_friendship")
public class Friendship {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String friendshipId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 好友ID
     */
    private Long friendId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 关联外键
     */
    @Transient
    private UserInfo friend;

    public String getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(String friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public UserInfo getFriend() {
        return friend;
    }

    public void setFriend(UserInfo friend) {
        this.friend = friend;
    }
}
