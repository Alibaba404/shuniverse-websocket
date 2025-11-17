package cn.shuniverse.websocket.core;

import cn.shuniverse.websocket.model.JoinGroupMessage;
import cn.shuniverse.websocket.model.WebSocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * Created by 蛮小满Sama at 2025-06-17 20:04
 *
 * @author 蛮小满Sama
 * @description 默认的WebSocket消息发送器
 */
public class WebSocketMessageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketMessageSender.class);

    private WebSocketMessageSender() {
    }

    /**
     * 发送消息
     *
     * @param session 会话
     * @param message JSON消息
     */
    public static void sendMessage(Session session, String message) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(new ObjectMapper().writeValueAsString(message));
            }
        } catch (IOException e) {
            LOGGER.error("发送消息失败", e);
        }
    }


    /**
     * 发送消息
     *
     * @param session 会话
     * @param message Java消息对象
     */
    public static void sendMessage(Session session, WebSocketMessage message) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendObject(message);
            }
        } catch (Exception e) {
            LOGGER.error("发送消息失败", e);
        }
    }

    /**
     * 发送消息给指定用户（多端同步）
     *
     * @param receiver 接收人ID
     * @param message  Java消息对象
     */
    public static void sendMessage2UserAllClient(String receiver, WebSocketMessage message) {
        // 获取用户所有客户端Session
        List<String> ids = WebSocketSessionManager.listSessionId(receiver);
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(sid -> sendMessage(WebSocketSessionManager.getWebSocketServerBySession(sid).session(), message));
        }
    }

    /**
     * 发送消息给指定用户指定群组所有客户端
     *
     * @param uid     发送人ID
     * @param gid     群聊ID
     * @param message Java消息对象
     */
    public static void send2Group(String uid, String gid, WebSocketMessage message) {
        WebSocketSessionManager.listSessionByGroup(gid).forEach(session -> sendMessage(session, message));
    }

    /**
     * 发送用户加入群组消息
     *
     * @param uid 用户ID
     * @param gid 群聊ID
     */
    public static void sendJoinGroupMessage(String uid, String gid) {
        // 获取群聊中全部用户会话对象
        Set<Session> sessions = WebSocketSessionManager.listSessionByGroup(gid);
        List<String> userAllSession = WebSocketSessionManager.listSessionId(uid);
        // 通知群聊中其他人群组有新的用户加入
        sessions.stream()
                // 排除自己的客户端
                .filter(session -> !userAllSession.contains(session.getId()))
                .forEach(session -> sendMessage(session, new JoinGroupMessage(uid, gid)));
    }
}
