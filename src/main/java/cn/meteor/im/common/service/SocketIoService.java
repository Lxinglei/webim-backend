package cn.meteor.im.common.service;

import cn.meteor.im.entity.Message;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Meteor
 */

@Component
public class SocketIoService {

    private Logger logger = LoggerFactory.getLogger(SocketIoService.class);

    @Autowired
    private SocketIOServer server;

    @Autowired
    private SocketIoClientCache clientCache;

    @Autowired
    SocketIoResponse socketIoResponse;

    public void sendMessage(Message message) {
        SocketIOClient toClient = clientCache.getClient(message.getReceiverId().toString());
        socketIoResponse.sendEvent(toClient, message);
    }
}
