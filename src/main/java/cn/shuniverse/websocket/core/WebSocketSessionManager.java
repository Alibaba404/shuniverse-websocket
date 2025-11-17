package cn.shuniverse.websocket.core;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 蛮小满Sama at 2025-06-17 20:00
 *
 * @author 蛮小满Sama
 * @description
 */
public class WebSocketSessionManager {
    // Key:sessionId,Value:WebSocketServer
    private static final Map<String, WebSocketEndpoint> SESSION_POOL = new ConcurrentHashMap<>();
    // 支持一个用户多个连接（多端登录）
    private static final Map<String, List<String>> USER_MULTI_PLATFORM = new ConcurrentHashMap<>();
    // 群组ID -> 在线会话集合
    private static final Map<String, Set<Session>> GROUP_SESSION_MAP = new ConcurrentHashMap<>();
    // 客户端在线数量
    private static int onlineCount = 0;

    private WebSocketSessionManager() {
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ++onlineCount;
    }

    public static synchronized void subOnlineCount() {
        --onlineCount;
    }


    /**
     * 给用户添加客户端
     *
     * @param uid      客户端ID
     * @param endpoint WebSocketEndpoint
     */
    public static void putClient(String uid, WebSocketEndpoint endpoint) {
        Session session = endpoint.session();
        SESSION_POOL.put(session.getId(), endpoint);
        if (USER_MULTI_PLATFORM.containsKey(uid)) {
            USER_MULTI_PLATFORM.get(uid).add(session.getId());
        } else {
            List<String> sessionIds = new ArrayList<>();
            sessionIds.add(session.getId());
            USER_MULTI_PLATFORM.put(uid, sessionIds);
        }
    }

    /**
     * 移除客户端
     *
     * @param session 会话
     */
    public static synchronized void removeClient(Session session) {
        if (SESSION_POOL.containsKey(session.getId())) {
            String uid = SESSION_POOL.get(session.getId()).uid();
            if (USER_MULTI_PLATFORM.containsKey(uid)) {
                USER_MULTI_PLATFORM.get(uid).remove(session.getId());
            }
            SESSION_POOL.remove(session.getId());
            subOnlineCount();
        }
    }

    /**
     * 获取端点对象
     *
     * @param sid 会话ID
     * @return
     */
    public static WebSocketEndpoint getWebSocketServerBySession(String sid) {
        return SESSION_POOL.getOrDefault(sid, null);
    }

    /**
     * 获取端点对象
     *
     * @param session 会话
     * @return
     */
    public static WebSocketEndpoint getWebSocketServerBySession(Session session) {
        return getWebSocketServerBySession(session.getId());
    }

    /**
     * 获取用全部客户端会话ID
     *
     * @param uid 用户ID
     * @return
     */
    public static List<String> listSessionId(String uid) {
        return USER_MULTI_PLATFORM.getOrDefault(uid, null);
    }

    /**
     * 加入群组
     *
     * @param gid     群组ID
     * @param session 会话
     */
    public static void joinGroup(String gid, Session session) {
        GROUP_SESSION_MAP.computeIfAbsent(gid, k -> ConcurrentHashMap.newKeySet()).add(session);
    }


    /**
     * 加入群组
     *
     * @param gid 群组ID
     * @param uid 用户ID
     */
    public static void joinGroup(String uid, String gid) {
        // 获取uid全部客户端会话对象（PC、H5、ios、android）加入到群组
        USER_MULTI_PLATFORM.get(uid).forEach(sid -> joinGroup(gid, SESSION_POOL.get(sid).session()));
    }

    /**
     * 获取群组所有会话对象
     *
     * @param gid 群组ID
     * @return
     */
    public static Set<Session> listSessionByGroup(String gid) {
        return GROUP_SESSION_MAP.getOrDefault(gid, null);
    }
}
