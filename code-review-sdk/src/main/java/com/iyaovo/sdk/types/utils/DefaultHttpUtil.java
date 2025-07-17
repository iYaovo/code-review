package com.iyaovo.sdk.types.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author Wuchubuzai
 * @version 1.0 2024年08月24日 23:51
 */
public class DefaultHttpUtil {

    /**
     * 发送GET请求
     * @param uri Get请求地址
     * @param headers 请求头
     * @return 字符串类型的返回结果
     * @throws Exception
     */
    public static String executeGetRequest(String uri, Map<String, String> headers) throws Exception {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // 设置请求头
        headers.forEach((key, value) -> connection.setRequestProperty(key, value));

        connection.setDoOutput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }
}
