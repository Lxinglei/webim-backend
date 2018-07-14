package cn.meteor.im.dto;

import cn.meteor.im.entity.Message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Meteor
 */
public class MessageHistory implements Serializable {

    private Long userId;

    private String username;

    private String avatar;

    private Date lastChatTime;

    private String lastChatContent;

    private int lastChatType;

    private List<MessageItem> messageList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastChatTime() {
        return lastChatTime;
    }

    public void setLastChatTime(Date lastChatTime) {
        this.lastChatTime = lastChatTime;
    }

    public String getLastChatContent() {
        return lastChatContent;
    }

    public void setLastChatContent(String lastChatContent) {
        this.lastChatContent = lastChatContent;
    }

    public List<MessageItem> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageItem> messageList) {
        this.messageList = messageList;
    }

    public int getLastChatType() {
        return lastChatType;
    }

    public void setLastChatType(int lastChatType) {
        this.lastChatType = lastChatType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
