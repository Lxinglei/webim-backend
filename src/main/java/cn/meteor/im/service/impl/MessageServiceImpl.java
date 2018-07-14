package cn.meteor.im.service.impl;

import cn.meteor.im.common.service.SocketIoClientCache;
import cn.meteor.im.common.service.SocketIoService;
import cn.meteor.im.dto.MessageExecutor;
import cn.meteor.im.dto.MessageHistory;
import cn.meteor.im.dto.MessageItem;
import cn.meteor.im.entity.Message;
import cn.meteor.im.entity.UserInfo;
import cn.meteor.im.enums.MessageStateEnum;
import cn.meteor.im.exceptions.MessageOperationException;
import cn.meteor.im.mapper.MessageMapper;
import cn.meteor.im.mapper.UserInfoMapper;
import cn.meteor.im.service.MessageService;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author Meteor
 */
@Service
public class MessageServiceImpl implements MessageService {


    private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SocketIoService socketIoService;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public MessageExecutor sendMessage(Message message) {

        try {
            // 1.补全消息结构
            UserInfo fromUser = userInfoMapper.selectByPrimaryKey(message.getSenderId());
            UserInfo toUser = userInfoMapper.selectByPrimaryKey(message.getReceiverId());
            message.setSender(fromUser);
            message.setReceiver(toUser);
            message.setStatus(MessageStateEnum.UNREAD.getState());
            message.setSenderName(fromUser.getNickName());
            message.setReceiverName(toUser.getNickName());
            message.setCreateTime(new Date());
            message.setLastUpdateTime(new Date());

            // 2.发送消息到用户
            logger.info("正在发送消息{}->{}:{}",
                    message.getSenderId(),
                    message.getReceiverId(),
                    message.getMsgContent());
            socketIoService.sendMessage(message);

            // 3.存储消息到数据库
            messageMapper.insert(message);

            // 4.返回结果
            return new MessageExecutor(MessageStateEnum.SUCCESS, message);
        } catch (Exception e) {
            logger.info("发送消息失败{}", e.getMessage());
            throw new MessageOperationException(e.getMessage());
        }
    }

    @Override
    public MessageExecutor getHistory(Long userId) {

        Assert.notNull(userId, "userId 不能为空");
        Example example = new Example(Message.class);
        example.createCriteria()
                .orEqualTo("senderId", userId)
                .orEqualTo("receiverId", userId);
        example.orderBy("createTime").desc();
        List<Message> messageList = messageMapper.selectByExample(example);
        List<MessageHistory> messageHistoryList = parseHistorys(userId, messageList);
        return new MessageExecutor(MessageStateEnum.SUCCESS, messageHistoryList);
    }

    private List<MessageHistory> parseHistorys(Long userId, List<Message> messageList) {
        List<MessageItem> messageItems = new ArrayList<>();
        messageList.forEach(message -> {
            messageItems.add(new MessageItem(userId, message));
        });

        Map<Long, List<MessageItem>> messageMap = new HashMap<>(1000);

        messageItems.forEach(messageItem -> {
            Long friendId =
                    messageItem.getSenderId().equals(userId) ? messageItem.getReceiverId(): messageItem.getSenderId();
            if (!messageMap.containsKey(friendId)) {
                messageMap.put(friendId, new ArrayList<>());
            }

            messageMap.get(friendId).add(messageItem);

        });

        List<MessageHistory> histories = new ArrayList<>();
        for (Long friendId : messageMap.keySet()) {
            List<MessageItem> messageItemList = messageMap.get(friendId);
            MessageItem lastMessage = messageItemList.get(0);
            MessageHistory history = new MessageHistory();
            history.setUserId(friendId);
            String username =
                    lastMessage.getSenderId().equals(userId) ? lastMessage.getReceiverName() : lastMessage.getSenderName();
            history.setUsername(username);
            history.setLastChatTime(lastMessage.getCreateTime());
            history.setLastChatContent(lastMessage.getMsgContent());
            history.setLastChatType(lastMessage.getType());
            history.setMessageList(messageItemList);

            history.setAvatar(userInfoMapper.selectByPrimaryKey(friendId).getAvatar());
            histories.add(history);
        }

        histories.sort(new Comparator<MessageHistory>() {
            @Override
            public int compare(MessageHistory o1, MessageHistory o2) {
                return -o1.getLastChatTime().compareTo(o2.getLastChatTime());
            }
        });
        return histories;
    }
}
