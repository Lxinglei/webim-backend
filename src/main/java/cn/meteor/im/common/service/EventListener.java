package cn.meteor.im.common.service;


import cn.meteor.im.common.bean.MsgBean;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Meteor
 */

@Component
public class EventListener {

    private Logger logger = LoggerFactory.getLogger(EventListener.class);

    @Autowired
    private SocketIoClientCache clientCache;

    @Autowired
    private SocketIoResponse socketIOResponse;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        logger.info("建立连接:{}", userId);
        clientCache.addClient(client, userId);
    }

    @OnEvent("OnMSG")
    public void onSync(SocketIOClient client, MsgBean bean) {
        logger.info("收到消息-from: {} to:{}\n", bean.getFrom(), bean.getTo());
        SocketIOClient ioClients = clientCache.getClient(bean.getTo());
        System.out.println("clientCache");
        if (ioClients == null) {
            logger.info("你发送消息的用户不在线");
            return;
        }
        socketIOResponse.sendEvent(ioClients,bean);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        logger.info("关闭连接");
    }
}
