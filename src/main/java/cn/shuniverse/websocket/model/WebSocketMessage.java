package cn.shuniverse.websocket.model;

import java.time.Clock;

/**
 * Created by 蛮小满Sama at 2025-06-17 17:16
 *
 * @author 蛮小满Sama
 * @description
 */
public abstract class WebSocketMessage {
    /**
     * 消息类型
     */
    protected String type;
    /**
     * 消息内容
     */
    protected String content;
    /**
     * 消息发送者
     */
    protected String sender;
    /**
     * 消息接收者
     */
    protected String receiver;

    /**
     * 消息发送时间戳
     */
    protected Long timestamp;

    protected WebSocketMessage() {
        this.timestamp = Clock.systemDefaultZone().millis();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
