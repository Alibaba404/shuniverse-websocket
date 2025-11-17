package cn.shuniverse.websocket.core.encoder;


import cn.shuniverse.websocket.model.WebSocketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by 蛮小满Sama at 2025-06-17 17:51
 *
 * @author 蛮小满Sama
 * @description WebSocketMessage 的 JSON 编码器,供 sendObject() 使用
 */
public class WebSocketMessageEncoder implements Encoder.Text<WebSocketMessage> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String encode(WebSocketMessage message) throws EncodeException {
        try {
            return OBJECT_MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new EncodeException(message, "WebSocketMessage 编码失败", e);
        }
    }

    @Override
    public void init(EndpointConfig config) {
        // 无需初始化操作
    }

    @Override
    public void destroy() {
        // 无需销毁操作
    }
}
