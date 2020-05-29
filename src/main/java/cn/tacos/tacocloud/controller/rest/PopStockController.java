package cn.tacos.tacocloud.controller.rest;

import cn.tacos.tacocloud.configuration.holder.ViewProps;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController(value = "StockController")
@RequestMapping(path = "/pop" , produces = "application/json") //produces定义响应数据格式
@CrossOrigin(origins = "*")
public class PopStockController {
    private ViewProps viewProps;
    @Autowired
    public void setViewProps(ViewProps viewProps) {
        this.viewProps = viewProps;
    }
    private JpaPopInStockRepository popInStockRepository;
    @Autowired
    public void setPopInStockRepository(JpaPopInStockRepository popInStockRepository) {
        this.popInStockRepository = popInStockRepository;
    }

    @GetMapping
    public String show() throws JsonProcessingException {
        List<PopInStock> list = popInStockRepository.findPopInStocksByOrderByTaskDesc(PageRequest.of(0,viewProps.getPageSize()));
        return new ObjectMapper().writeValueAsString(list);
    }
    //查询数据
    @GetMapping("/{id}")
    public ResponseEntity<PopInStock> getById(@PathVariable("id") int id){
        Optional<PopInStock> optPop =  Optional.ofNullable(popInStockRepository.findById(id));
        return optPop.map(popInStock -> new ResponseEntity<>(popInStock, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
    //插入数据
    @PostMapping(consumes = "application/json") //consumes定义请求数据类型
    @ResponseStatus(HttpStatus.CREATED)
    public void postPop(@RequestBody PopInStock pop){
        popInStockRepository.save(pop);
    }
    //全部更新
    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPop(@RequestBody PopInStock pop){
        popInStockRepository.save(pop);
    }
    //部分更新
    @PatchMapping(consumes = "application/json",path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchPop(@RequestBody PopInStock pop,@PathVariable("id") int id){
        PopInStock origin = popInStockRepository.findById(id);
        if(pop.getTask() != null) origin.setTask(pop.getTask());
        if(pop.getCode() != null) origin.setCode(pop.getCode());
        if(pop.getName() != null) origin.setName(pop.getName());
        if(pop.getVersion() != null) origin.setVersion(pop.getVersion());
        if(pop.getBrand() != null) origin.setBrand(pop.getBrand());
        if(pop.getInStockCount() != null) origin.setInStockCount(pop.getInStockCount());
        if(pop.getComment() != null) origin.setComment(pop.getComment());

        popInStockRepository.save(origin);
    }
    //删除
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int id){
        try{
            popInStockRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){}
    }
}
