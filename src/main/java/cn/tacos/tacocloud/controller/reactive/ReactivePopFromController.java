package cn.tacos.tacocloud.controller.reactive;

import cn.tacos.tacocloud.domain.jpa.PopFrom;
import cn.tacos.tacocloud.domain.jpa.PopInStockUDT;
import cn.tacos.tacocloud.repository.reactive.ReactivePopFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reactive/popFrom")
public class ReactivePopFromController {
    @Autowired
    private ReactivePopFromRepository popFromRepository;
    @GetMapping("/recent")
    @ResponseBody
    public Flux<PopFrom> recent(){
        return popFromRepository.findAll().take(12);
    }
    @GetMapping("/get/{id}")
    @ResponseBody
    public Mono<PopFrom> getById(@PathVariable int id){
        return popFromRepository.findById(id);
    }
    @GetMapping("/getBy/{createBy}")
    @ResponseBody
    public Flux<PopFrom> getByCreateBy(@PathVariable String createBy){
        return popFromRepository.getByCreateBy(createBy);
    }
    @GetMapping("/save/{id}")
    @ResponseBody
    public Mono<PopFrom> save(@PathVariable int id){
        PopFrom popFrom = new PopFrom();
        popFrom.setCreateBy("Tom"+id);
        popFrom.setCreateDate(LocalDate.now());
        popFrom.setId(id);

        PopInStockUDT popUDT = new PopInStockUDT();
        popUDT.setName("AAA"+id);
        popUDT.setCode("111");

        PopInStockUDT popUDT2 = new PopInStockUDT();
        popUDT2.setName("BBB"+id);
        popUDT2.setCode("222");

        popFrom.setPops(List.of(popUDT,popUDT2));
        return popFromRepository.save(popFrom);
    }
}
