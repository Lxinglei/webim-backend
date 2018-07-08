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

    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration config = new Configuration();
        config.setPort(10010);

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        config.setSocketConfig(socketConfig);
        config.setHostname("0.0.0.0");

        SocketIOServer server = new SocketIOServer(config);
        server.addListeners(eventListener);
        server.startAsync();
        logger.info("即时通讯服务器启动成功...");
    }
}
