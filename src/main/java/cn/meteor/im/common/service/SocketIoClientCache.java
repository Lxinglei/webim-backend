package cn.meteor.im.common.service;

import cn.meteor.im.common.bean.MsgBean;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Meteor
 */
@Component
public class SocketIoClientCache {
    private Map<String,SocketIOClient> clients=new ConcurrentHashMap<String,SocketIOClient>();

    /**
     * 用户发送消息添加
     * @param client
     */
    public void addClient(SocketIOClient client, String userId){
        clients.put(userId,client);
    }

    /**
     * 用户退出时移除
     * @param msgBean
     */
    public void remove(MsgBean msgBean) {
        clients.remove(msgBean.getFrom());
    }

    /**
     * 获取所有
     * @param to
     * @return
     */
    public SocketIOClient getClient(String to) {
        return clients.get(to);
    }
}
