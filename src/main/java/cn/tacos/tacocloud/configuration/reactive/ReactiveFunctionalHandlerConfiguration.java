package cn.tacos.tacocloud.configuration.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.reactive.ReactiveJpaPopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.servlet.function.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Configuration
public class ReactiveFunctionalHandlerConfiguration {
    //@Bean
    public RouterFunction<?> routerFunction(){
        return RouterFunctions.route(RequestPredicates.GET("/helloWorld"),
                        serverRequest -> ServerResponse.ok().body(Mono.just("Hello World!")))
                .andRoute(RequestPredicates.POST("/bye"),
                        serverRequest -> ServerResponse.ok().body(Mono.just("Bye"), new ParameterizedTypeReference<Mono<? super String>>(){}));
    }
    //@Bean
    public RouterFunction<?> popRouterFunction(){
        return RouterFunctions.route(RequestPredicates.GET("/reactive/pop/{id}"),this::popById)
                .andRoute(RequestPredicates.POST("/reactive/pop/recent"),this::recent);
    }
    //@Autowired
    private ReactiveJpaPopRepository reactiveJpaPopRepository;
    public ServerResponse popById(ServerRequest serverRequest) throws Exception {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return ServerResponse.ok().body(ServerResponse.ok().body(reactiveJpaPopRepository.findById(id)));
    }
    public ServerResponse recent(ServerRequest serverRequest) throws Exception {
        return ServerResponse.ok().body(reactiveJpaPopRepository.findAll().take(12));
    }
}
