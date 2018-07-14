package cn.meteor.im.dto;

import cn.meteor.im.entity.Message;
import cn.meteor.im.entity.Message;
import cn.meteor.im.enums.MessageStateEnum;

import java.util.List;

/**
 * @author Meteor
 */
public class MessageExecutor extends BaseExecutor {

    private Message chatMessage;

    private List<MessageHistory> messageHistoryList;

    public MessageExecutor(MessageStateEnum MessageStateEnum) {
        this.setState(MessageStateEnum.getState());
        this.setMessage(MessageStateEnum.getMessage());
    }



    public MessageExecutor(MessageStateEnum MessageStateEnum, Message chatMessage) {
        this.setState(MessageStateEnum.getState());
        this.setMessage(MessageStateEnum.getMessage());
        this.setChatMessage(chatMessage);
    }

    public MessageExecutor(MessageStateEnum MessageStateEnum, List<MessageHistory> messageHistoryList) {
        this.setState(MessageStateEnum.getState());
        this.setMessage(MessageStateEnum.getMessage());
        this.setMessageHistoryList(messageHistoryList);
    }

    public Message getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(Message chatMessage) {
        this.chatMessage = chatMessage;
    }

    public List<MessageHistory> getMessageHistoryList() {
        return messageHistoryList;
    }

    public void setMessageHistoryList(List<MessageHistory> messageHistoryList) {
        this.messageHistoryList = messageHistoryList;
    }
}
