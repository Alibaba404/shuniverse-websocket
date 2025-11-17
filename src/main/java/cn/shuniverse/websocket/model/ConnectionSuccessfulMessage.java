package cn.shuniverse.websocket.model;


import cn.shuniverse.websocket.enums.WebSocketMessageCategoryEnum;
import org.apache.commons.lang3.StringUtils;

import java.time.Clock;

/**
 * Created by 蛮小满Sama at 2025-06-17 17:58
 *
 * @author 蛮小满Sama
 * @description
 */
public class ConnectionSuccessfulMessage extends WebSocketMessage {
    public ConnectionSuccessfulMessage(String receiver) {
        setType(WebSocketMessageCategoryEnum.CONNECTION_SUCCESSFUL.getType());
        setContent(WebSocketMessageCategoryEnum.CONNECTION_SUCCESSFUL.getMessage());
        setSender(WebSocketMessageCategoryEnum.CONNECTION_SUCCESSFUL.getSender());
        setReceiver(StringUtils.isNotBlank(receiver) ? receiver : WebSocketMessageCategoryEnum.CONNECTION_SUCCESSFUL.getReceiver());
        setTimestamp(Clock.systemUTC().millis());
    }
}
