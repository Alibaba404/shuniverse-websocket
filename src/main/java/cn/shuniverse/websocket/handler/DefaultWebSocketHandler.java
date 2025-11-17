package cn.shuniverse.websocket.handler;

import cn.shuniverse.websocket.core.WebSocketEndpoint;
import cn.shuniverse.websocket.core.WebSocketMessageSender;
import cn.shuniverse.websocket.core.WebSocketSessionManager;
import cn.shuniverse.websocket.model.ConnectionSuccessfulMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

/**
 * Created by 蛮小满Sama at 2025-06-17 20:06
 *
 * @author 蛮小满Sama
 * @description
 */
public class DefaultWebSocketHandler implements IWebsocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWebSocketHandler.class);

    @Override
    public void onOpen(Session session, String uid) {
        WebSocketMessageSender.sendMessage(session, new ConnectionSuccessfulMessage(uid));
        LOGGER.info("DefaultWebSocketHandler-连接成功: uid={}, 当前在线={}", uid, WebSocketSessionManager.getOnlineCount());
    }

    @Override
    public void onMessage(Session session, String message) {
        WebSocketEndpoint endpoint = WebSocketSessionManager.getWebSocketServerBySession(session);
        LOGGER.info("DefaultWebSocketHandler-收到消息: uid={}, content={}", endpoint.uid(), message);
    }

    @Override
    public void onClose(Session session) {
        // 获取端点对象
        WebSocketEndpoint endpoint = WebSocketSessionManager.getWebSocketServerBySession(session);
        // 移除客户端
        WebSocketSessionManager.removeClient(session);
        LOGGER.info("DefaultWebSocketHandler-连接断开: uid={}, 剩余在线={}", endpoint.uid(), WebSocketSessionManager.getOnlineCount());
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        // 获取端点对象
        WebSocketEndpoint endpoint = WebSocketSessionManager.getWebSocketServerBySession(session);
        // 移除客户端
        WebSocketSessionManager.removeClient(session);
        LOGGER.error("DefaultWebSocketHandler-连接异常: uid={}，剩余在线={}，异常信息={}", endpoint.uid(), WebSocketSessionManager.getOnlineCount(), throwable.getMessage());
    }
}
