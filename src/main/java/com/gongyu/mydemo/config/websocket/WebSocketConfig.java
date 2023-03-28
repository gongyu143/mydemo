package com.gongyu.mydemo.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
/**
 * @author wengwx
 * @date 2023/3/28
 * @des
 */

@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    private final HttpAuthHandler httpAuthHandler;

    @Autowired
    public WebSocketConfig(HttpAuthHandler httpAuthHandler) {
        this.httpAuthHandler = httpAuthHandler;
    }

    /**
     * websocket 链接配置
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "")
                .setAllowedOrigins("*");
    }
}
