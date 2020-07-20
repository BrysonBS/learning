package cn.tacos.tacocloud.controller.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("reactive/live")
public class ReactiveController {
    @Autowired
    private JpaPopInStockRepository jpaPopInStockRepository;
    @GetMapping("/recent")
    @ResponseBody
    public Flux<PopInStock> recent(){
        Pageable pageable = PageRequest.of(1,12);
        return Flux.fromIterable(jpaPopInStockRepository.findAll(pageable));
    }
}
