package cn.meteor.im.common.service;

import cn.meteor.im.common.bean.MsgBean;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

/**
 * @author Meteor
 */
@Component
public class SocketIoResponse {
    public void sendEvent(SocketIOClient client, MsgBean bean) {
        client.sendEvent("OnMSG", bean);
    }
}
