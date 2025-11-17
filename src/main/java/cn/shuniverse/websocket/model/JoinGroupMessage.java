package cn.shuniverse.websocket.model;


import cn.shuniverse.websocket.enums.WebSocketMessageCategoryEnum;

import java.time.Clock;

/**
 * Created by 蛮小满Sama at 2025-06-17 17:58
 *
 * @author 蛮小满Sama
 * @description
 */
public class JoinGroupMessage extends WebSocketMessage {

    /**
     * 进入群聊用户ID
     */
    private String uid;
    /**
     * 进入的群聊ID
     */
    private String gid;

    private JoinGroupMessage() {
    }

    public JoinGroupMessage(String uid, String gid) {
        setType(WebSocketMessageCategoryEnum.JOIN_GROUP.getType());
        setContent(WebSocketMessageCategoryEnum.JOIN_GROUP.getMessage());
        setSender(WebSocketMessageCategoryEnum.JOIN_GROUP.getSender());
        setReceiver(WebSocketMessageCategoryEnum.JOIN_GROUP.getReceiver());
        setTimestamp(Clock.systemUTC().millis());
        setGid(gid);
        setUid(uid);
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
