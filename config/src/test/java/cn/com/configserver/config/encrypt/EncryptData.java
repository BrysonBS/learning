package cn.com.configserver.config.encrypt;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EncryptData {
    @Test
    public void getData() throws IOException, InterruptedException {
        String value = "8765";
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type","text/plain")
                .uri(URI.create("http://localhost:8888/encrypt"))
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .build();
        String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(body);
    }
    @Test
    public void decrypt() throws IOException, InterruptedException {
        String value = "0c4e97a951510731b47ab0bc81f62ceab517f28bec007e36c6a1654773c1d4a1";
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type","text/plain")
                .uri(URI.create("http://localhost:8888/decrypt"))
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .build();
        String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(body);
    }
}
