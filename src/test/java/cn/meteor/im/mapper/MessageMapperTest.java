package cn.meteor.im.mapper;

import cn.meteor.im.BaseTest;
import cn.meteor.im.entity.Message;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;

import static org.junit.Assert.*;

public class MessageMapperTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(MessageMapperTest.class);

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void insert() {
        Message message = new Message();
        message.setSenderId(1L);
        message.setReceiverId(2L);
        message.setMsgContent("你好");
        message.setStatus(0);
        message.setSenderName("吴彦祖");
        message.setReceiverName("宋小宝");
        message.setCreateTime(new Date());
        message.setLastUpdateTime(new Date());

        int count = messageMapper.insert(message);

        Assert.assertEquals(1, count);
        logger.info(message.getMessageId());
    }
}