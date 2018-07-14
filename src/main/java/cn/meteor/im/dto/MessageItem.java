package cn.meteor.im.dto;

import cn.meteor.im.entity.Message;
import org.springframework.beans.BeanUtils;

/**
 * @author Meteor
 */
public class MessageItem extends Message {

    public MessageItem(Long currentUserId, Message message) {
        BeanUtils.copyProperties(message, this);
        if (currentUserId.equals(message.getSenderId())) {
            this.type = 1;
        } else {
            this.type = 2;
        }
    }

    /**
     * 类型：1-发送，2-接收
     */


    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
