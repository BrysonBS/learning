package cn.tacos.tacocloud.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveLiveServerTest {
    @Autowired
    private WebTestClient testClient;
    @Test
    public void testRecent(){
        testClient.get().uri("/reactive/live/recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
                //.expectBodyList(PopInStock.class)
                //.isEqualTo()
    }
}
