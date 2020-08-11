package cn.tacos.tacocloud.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(ReactiveTestConfiguration.class)
public class ReactiveWebClientTest {
    @Autowired
    private WebClient webClient;
    @Test
    public void sendTest(){
        PopInStock popInStock = new PopInStock();
        popInStock.setId(120);
        popInStock.setName("AAAAAAA");
        Mono<PopInStock> popMono = Mono.just(popInStock);

/*        //1. 使用Mono对象创建
        webClient.post()
                .uri("/api/popInStocks")
                .body(popMono, PopInStock.class)
                .retrieve()
                .toBodilessEntity()
                .subscribe(e -> System.out.println(e.getStatusCode()));*/

        //2. 使用原始对象创建
        webClient.post()
                .uri("/api/popInStocks")
                .bodyValue(popInStock)
                .retrieve()
                .toBodilessEntity()
                .subscribe(e -> System.out.println(e.getStatusCode()));

        //3. 使用PUT请求
        webClient.put()
                .uri("/api/popInStocks/{id}",1)
                .body(popMono, PopInStock.class)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();

        //4. 使用DELETE请求
        webClient.delete()
                .uri("/api/popInStocks/{id}",1)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
