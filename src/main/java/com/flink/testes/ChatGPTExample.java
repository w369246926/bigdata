package com.flink.testes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ChatGPTExample {
    public static void main(String[] args) {
        try {
            String prompt = "Hello, how are you?"; // 设置初始对话提示

            String api_key = "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"; // 替换为您的OpenAI API密钥
            String api_url = "https://api.openai.com/v1/engines/davinci-codex/completions"; // 指定OpenAI API的URL

            // 创建URL对象
            URL url = new URL(api_url);

            // 创建HTTP连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置HTTP请求头
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + api_key);

            // 设置HTTP请求正文
            String data = "{prompt: " + prompt + ", max_tokens: 150, temperature: 0.5}";
            conn.setDoOutput(true);
            conn.getOutputStream().write(data.getBytes());

            // 发送HTTP请求并读取响应
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            // 解析OpenAI API响应并输出结果
            String response = sb.toString();
            String[] results = response.split("\"text\": \"");
            for (int i = 1; i < results.length; i++) {
                String result = results[i].split("\"")[0];
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

