package com.gongyu.mydemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.*;
import java.net.Socket;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

/**
 * @author wengwx
 * @date 2022/12/13
 * @des websocket 客户端client
 */

@Slf4j
public class MyWebSocketClient extends WebSocketClient {


    public MyWebSocketClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {
        super.onWebsocketPing(conn, f);
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        try {
            Thread.sleep(60000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===============pong===========");
        this.onWebsocketPing(conn,f);
        super.onWebsocketPong(conn, f);
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("握手...");
        for (Iterator<String> it = shake.iterateHttpFields(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + ":" + shake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String paramString) {
        System.out.println("接收到消息：" + paramString);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected"+" :"+code+" :"+reason);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("异常: " + e);
    }

    public static void trustAllHosts(WebSocketClient appClient) {
        System.out.println("wss");
        TrustManager[] trustAllCerts = new TrustManager[]{new X509ExtendedTrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory factory = sc.getSocketFactory();
            appClient.setSocketFactory(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MyWebSocketClient getChatClient(String url)throws Exception {
        MyWebSocketClient chatclient = new MyWebSocketClient(new URI(url));
        trustAllHosts(chatclient);
        chatclient.connectBlocking();
        return chatclient;
    }

    public static void main(String[] args) throws Exception {

        MyWebSocketClient chatclient = new MyWebSocketClient(new URI("ws://localhost:8080/websocket/1/1"));
//        trustAllHosts(chatclient);
        System.out.printf(" 1233 ..  ");
        chatclient.connect();
        Thread.sleep(5000);
        chatclient.send("111");


    }
}
