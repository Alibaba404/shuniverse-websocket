package cn.shuniverse.websocket.core;

import cn.shuniverse.websocket.core.encoder.WebSocketMessageEncoder;
import cn.shuniverse.websocket.handler.IWebsocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.shuniverse.base.utils.SpringContextHolder;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by 蛮小满Sama at 2025-06-17 20:00
 *
 * @author 蛮小满Sama
 * @description
 */
@Component
@ServerEndpoint(value = "/ws/{uid}", encoders = {WebSocketMessageEncoder.class})
public class WebSocketEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpoint.class);

    private String uid;
    private Session session;

    public Session session() {
        return session;
    }

    public String uid() {
        return uid;
    }

    public IWebsocketHandler handler() {
        return SpringContextHolder.getBean(IWebsocketHandler.class);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {
        this.uid = uid;
        this.session = session;
        // 在线数加1
        WebSocketSessionManager.addOnlineCount();
        // 添加到在线列表
        WebSocketSessionManager.putClient(uid, this);
        handler().onOpen(session, uid);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        handler().onMessage(session, message);
    }

    @OnClose
    public void onClose(Session session) {
        handler().onClose(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        handler().onError(session, error);
    }
}
