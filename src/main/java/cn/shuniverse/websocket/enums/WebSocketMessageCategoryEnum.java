package cn.shuniverse.websocket.enums;


import cn.shuniverse.websocket.constants.WebSocketConstants;

/**
 * Created by 蛮小满Sama at 2025-06-17 18:01
 *
 * @author 蛮小满Sama
 * @description
 */
public enum WebSocketMessageCategoryEnum {


    CONNECTION_SUCCESSFUL(WebSocketConstants.SYSTEM_USER_ID, WebSocketConstants.RECEIVER_USER_ID, "CONNECTION_SUCCESSFUL", "连接成功"),
    JOIN_GROUP(WebSocketConstants.SYSTEM_USER_ID, WebSocketConstants.RECEIVER_USER_ID, "JOIN_GROUP", "进入群聊"),
    ;


    private final String sender;
    private final String receiver;
    private final String type;
    private final String message;

    WebSocketMessageCategoryEnum(String sender, String receiver, String type, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
