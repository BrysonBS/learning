package cn.tacos.tacocloud.reactive;

import cn.tacos.tacocloud.controller.reactive.ReactivePopController;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.reactive.ReactiveJpaPopRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveTest {
    @Test
    public void testRecent() throws JsonProcessingException {
        PopInStock[] pops = new PopInStock[12];
        for(int i=0;i<pops.length;++i){
            pops[i] = getPop(i+1);
        }
        ReactiveJpaPopRepository reactiveJpaPopRepository = Mockito.mock(ReactiveJpaPopRepository.class);
        //当执行findAll方法时返回封装pops的flux对象
        Mockito.when(reactiveJpaPopRepository.findAll()).thenReturn(Flux.fromArray(pops));
        WebTestClient testClient = WebTestClient.bindToController(new ReactivePopController(reactiveJpaPopRepository))
                .build();
        //GET请求测试:
        testClient.get().uri("/reactive/pop/recent")
                .accept(MediaType.APPLICATION_JSON) //接收的类型
                .exchange()//发送请求
                .expectStatus().isOk()
                .expectBody()//获取到响应体内容
                .jsonPath("$").isNotEmpty()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(pops[0].getId()) //验证单个属性
                .json(new ObjectMapper().writeValueAsString(pops)); //直接对比json字符串是否相同
        //POST请求测试
        int id = 1;
        PopInStock popInStock = getPop(id);
        Mockito.when(reactiveJpaPopRepository.findById(id)).thenReturn(Mono.just(popInStock));
        testClient.post().uri("/reactive/pop/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PopInStock.class)
                .contains(popInStock);
    }
    private PopInStock getPop(int id){
        PopInStock pop = new PopInStock();
        pop.setId(id);
        pop.setName("PopInStock#" + id);
        return pop;
    }
}
