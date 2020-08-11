package cn.tacos.tacocloud.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(ReactiveTestConfiguration.class)
//@ContextConfiguration(classes = ReactiveTestConfiguration.class)
public class ReactiveLiveServerTest {
/*    @Autowired
    private WebTestClient testClient;
    @Test
    public void testRecent(){
        testClient.get().uri("/reactive/live/recent")
                //.uri("/reactive/live/recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }*/


    @Autowired
    private WebClient webClient;
    @Test
    public void testReactiveGetById(){
        Mono<PopInStock> popMono = WebClient.create()
                .get()
                .uri("http://localhost:9090/api/popInStocks/{id}",115)
                .retrieve()
                .bodyToMono(PopInStock.class);
        //popMono.subscribe(System.out::println);//消费

        //创建基本路径的WebClient
        Flux<PopInStock> popFlux = //WebClient.create("http://localhost:9090").get()
                webClient.get()
                .uri("/reactive/live/recent")
                .retrieve()
                .bodyToFlux(PopInStock.class);
        popFlux.timeout(Duration.ofSeconds(1)) //设置超时等待时间
                .subscribe(
                        System.out::println
                        ,error -> { //处理超时异常
                           System.out.println("超时!");
                        }
                );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReactiveError(){
        Mono<PopInStock> popMono =
                WebClient.create("http://localhost:9090").get()
                //webClient.get()
                .uri("/api/popInStocks/{id}",1)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, //或status -> status == HttpStatus.NOT_FOUND
                        response -> Mono.just(new Exception("错误!")))
                .bodyToMono(PopInStock.class);

        //消费
        popMono.subscribe(
                System.out::println, //e -> {}
                Throwable::printStackTrace //error -> {}
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReactiveExchange(){
        //1. 等价于retrieve方法使用
        Mono<PopInStock> popMono = webClient.get()
                .uri("/api/popInStocks/{id}",1)
                .exchange()
                .flatMap(e -> e.bodyToMono(PopInStock.class));
        popMono.subscribe(System.out::println);

        //2. 不同与retrieve使用,可以处理响应头headers,cookies等
        Mono<PopInStock> popInStockMono = webClient.get()
                .uri("/api/popInStocks/{id}",2)
                .exchange()
                .flatMap(e -> {
                    if(e.headers().header("X_UNAVAILABLE").contains("true")) //如果响应头存在此内容则返回空Mono对象
                        return Mono.empty();
                    return Mono.just(e);//否则返回Mono<ClientResponse>
                })
                .flatMap(e -> e.bodyToMono(PopInStock.class));

        //消费
        popInStockMono.subscribe(System.out::println);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test(){
        Flux<PopInStock> popFlux = webClient.get()
                .uri("/reactive/live/recent")
                .retrieve()
                .bodyToFlux(PopInStock.class);
        popFlux.subscribe(e -> System.out.println(e));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
