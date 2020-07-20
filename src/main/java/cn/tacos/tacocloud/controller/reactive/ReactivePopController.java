package cn.tacos.tacocloud.controller.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.reactive.ReactiveJpaPopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/reactive/pop")
public class ReactivePopController {
    private ReactiveJpaPopRepository reactiveJpaPopRepository;
    @Autowired
    public ReactivePopController(ReactiveJpaPopRepository reactiveJpaPopRepository) {
        this.reactiveJpaPopRepository = reactiveJpaPopRepository;
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Mono<PopInStock> popById(@PathVariable("id") int id){
        return reactiveJpaPopRepository.findById(id);
    }
    @GetMapping("/recent")
    @ResponseBody
    public Flux<PopInStock> recent(){
        return reactiveJpaPopRepository.findAll().take(12);
    }
}
