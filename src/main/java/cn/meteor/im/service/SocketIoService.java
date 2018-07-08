package cn.meteor.im.service;

/**
 * @author Meteor
 */
public interface SocketIoService {

    /**
     * 启动WebSocket服务
     */
    void startServer();

    /**
     * 停止WebSocket服务
     */
    void stopServer();

    /**
     * 给所有客户端发送消息
     * @param eventType
     * @param message
     */
    void sendMessageToAllClient(String eventType, String message);

    /**
     * 给指定客户端发送消息
     * @param uuid
     * @param eventType
     * @param message
     */
    void sendMessageToSpecificClient(String uuid, String eventType, String message);
}
