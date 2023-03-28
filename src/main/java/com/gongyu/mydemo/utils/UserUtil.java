package com.gongyu.mydemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/**
 * @author  wenxue
 * @date    2023/3/27 23:02
 * @desc    调用第三方接口  的工具类
**/
@Slf4j
@Component
public class UserUtil {

    private UserUtil() {}


    public static final String url_1 = "www.baidu.com";
    public String appadd() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // 创建连接
            URL url = new URL(url_1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            //设置请求头
            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();

            // POST请求,包装成json数据
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            Map map = new HashMap();
            out.writeBytes(JsonUtils.parseObjToJson(map));
            out.flush();
            out.close();

            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            stringBuffer = sb;
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tokenJson =  stringBuffer.toString();
        return tokenJson;
    }
}
