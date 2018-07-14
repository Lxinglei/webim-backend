package cn.meteor.im.common.service;

import cn.meteor.im.common.bean.MsgBean;
import cn.meteor.im.entity.Message;
import com.corundumstudio.socketio.SocketIOClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Meteor
 */
@Component
public class SocketIoResponse {

    private Logger logger = LoggerFactory.getLogger(SocketIoResponse.class);
    public void sendEvent(SocketIOClient client, Message bean) {
        logger.info("正在发送消息（IM）-from: {} to:{}\n", bean.getSenderId(), bean.getReceiverId());
        client.sendEvent("OnMSG", bean);
    }
}
