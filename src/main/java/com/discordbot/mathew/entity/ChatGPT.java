package com.discordbot.mathew.entity;

import com.discordbot.mathew.dto.GPTResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class ChatGPT {
    @Value("${api.url}")
    private String url;
    @Value("${api.key}")
    private String apiKey;
    @Value("${api.model}")
    private String model;

    @Deprecated
    public String process(String prompt) {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", " +
                    "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";

            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return (response.toString().split("\"content\": \"")[1].split("\"")[0]).substring(4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String responseTo(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"model\": \"" + model + "\", " +
                "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<GPTResponseDto> response = restTemplate.postForEntity(url, request, GPTResponseDto.class);

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
