package cn.shuniverse.websocket.handler;

import javax.websocket.Session;

/**
 * Created by 蛮小满Sama at 2025-06-17 20:05
 *
 * @author 蛮小满Sama
 * @description
 */
public interface IWebsocketHandler {
    void onOpen(Session session, String uid);

    void onMessage(Session session, String message);

    default void onClose(Session session) {
    }

    default void onError(Session session, Throwable throwable) {
    }
}
