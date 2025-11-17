package cn.shuniverse.websocket.config;

import cn.shuniverse.websocket.handler.DefaultWebSocketHandler;
import cn.shuniverse.websocket.handler.IWebsocketHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * Created by 蛮小满Sama at 2025-06-17 17:14
 *
 * @author 蛮小满Sama
 * @description
 */
@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig {
    /**
     * 创建一个ServerEndpointExporter对象，该对象会自动注册使用了@ServerEndpoint注解的类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    @ConditionalOnMissingBean(IWebsocketHandler.class)
    public IWebsocketHandler defaultWebsocketHandler() {
        return new DefaultWebSocketHandler();
    }
}
