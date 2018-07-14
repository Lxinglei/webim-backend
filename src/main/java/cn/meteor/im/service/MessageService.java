package cn.meteor.im.service;

import cn.meteor.im.dto.MessageExecutor;
import cn.meteor.im.entity.Message;

/**
 * @author Meteor
 */
public interface MessageService {

    /**
     * 发送消息
     * @param message
     * @return
     */
    MessageExecutor sendMessage(Message message);

    /**
     * 查询历史消息
     * @param userId
     * @return
     */
    MessageExecutor getHistory(Long userId);
}
