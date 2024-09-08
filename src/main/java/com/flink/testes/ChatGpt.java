package com.flink.testes;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class ChatGpt {
    public static void main(String[] args) throws IOException {
        // API endpoint and authentication information
        String endpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
        String apiKey = "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";

        // Request parameters
        String prompt = "Hello, world!";
        String model = "davinci-codex";
        int maxTokens = 10;

        // Construct the request body as a JSON object
        String requestBody = "{"
                + "\"prompt\": \"" + prompt + "\","
                + "\"model\": \"" + model + "\","
                + "\"max_tokens\": " + maxTokens
                + "}";

        // Send the HTTP request
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(requestBody);
        out.close();

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer response = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // Parse the response as a JSON object
        String jsonResponse = response.toString();

    }

}
