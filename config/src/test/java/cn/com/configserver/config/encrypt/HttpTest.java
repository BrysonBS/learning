package cn.com.configserver.config.encrypt;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpTest {
    @Test
    public void send() throws IOException, InterruptedException {
        String value = "8765";
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type","text/plain")
                .header("X-Config-Token","roottoken")
                .uri(URI.create("http://localhost:8888/profiles/registry-Vault"))
                .GET()
                //.POST(HttpRequest.BodyPublishers.ofString(value))
                .build();
        String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println(body);
    }
}
