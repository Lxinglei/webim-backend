package cn.meteor.im.common.service;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Meteor
 */
@Component
public class SocketIoInitializer implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(SocketIoInitializer.class);

    @Autowired
    private EventListener eventListener;

    @Autowired
    private SocketIOServer server;

    @Override
    public void afterPropertiesSet() throws Exception {

        server.addListeners(eventListener);
        server.startAsync();
        logger.info("即时通讯服务器启动成功...");
    }
}
