package com.gongyu.mydemo.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.net.URI;

/**
 * @author wengwx
 * @date 2023/3/28
 * @des
 */

@Slf4j
@Component
public class HttpAuthHandler extends AbstractWebSocketHandler {


    @Override
    protected void handleBinaryMessage(@NonNull WebSocketSession session, @NonNull BinaryMessage message) throws Exception {
        session.setBinaryMessageSizeLimit(1024*1024*10);
        super.handleBinaryMessage(session, message);
        try {
            byte[] bytes = new byte[message.getPayloadLength()];
            message.getPayload().get(bytes);
        } catch (Exception e) {
            log.error("读取数据异常", e);
        }
    }

    /**
     * 链接后操作
     *
     * @param session session
     * @throws Exception Exception
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        session.setBinaryMessageSizeLimit(1024*1024*10);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("session close");

    }
}
