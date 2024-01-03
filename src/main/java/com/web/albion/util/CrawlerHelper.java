package com.web.albion.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CrawlerHelper {

    private ObjectMapper objectmapper;

    public CrawlerHelper() {
        this.objectmapper = new ObjectMapper();
    }

    /**
     * Get Json data from URL
     *
     * @param url
     * @return JsonNode type
     */
    public JsonNode getJsonFromUrl(String url) throws Exception {
        URL url_api = new URL(url);

        int retryCount = 0;
        while (retryCount < 10) { // 최대 10번까지 재시도
            HttpURLConnection conn = (HttpURLConnection) url_api.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();


            if (responseCode != 200) {
                System.out.println("Response Code: " + responseCode);
                // 응답이 200이 아닌 경우 5초 대기 후 재시도
                Thread.sleep(5000); // 5초 대기
                retryCount++;
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String jsonResponse = response.toString();
                JsonNode jsonNode = this.objectmapper.readTree(jsonResponse);
                conn.disconnect();
                return jsonNode;
            }
        }

        throw new Exception("Failed to fetch data after 3 attempts");
    }
}
