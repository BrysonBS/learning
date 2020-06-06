package cn.tacos.tacocloud.controller.rest;

import cn.tacos.tacocloud.domain.assembler.PopInStockAssembler;
import cn.tacos.tacocloud.domain.assembler.PopInStockModel;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@ExposesResourceFor(PopInStock.class)  //暴露此类可供其他类使用
@RequestMapping("/popInStocks")
public class PopController {
    private JpaPopInStockRepository popInStockRepository;
    private EntityLinks links;
    @Autowired
    public void setPopInStockRepository(JpaPopInStockRepository popInStockRepository) {
        this.popInStockRepository = popInStockRepository;
    }
    @Autowired
    public void setLinks(EntityLinks links) {
        this.links = links;
    }

    @GetMapping("/list")
    public CollectionModel<PopInStockModel> popInStocks(){
        PageRequest page = PageRequest.of(0,2, Sort.by("task"));

        Link link = null;
        //link = links.linkToCollectionResource(PopInStock.class).withRel("recents");
        link = links.linkFor(PopInStock.class)
                .slash("/list") //添加额外路径
                .withRel("recents"); //设置生成的json字段名
        List<PopInStock> list = popInStockRepository.findAll(page);
        CollectionModel<PopInStockModel> collectionModel =
                new PopInStockAssembler(this.getClass()).toCollectionModel(list);//集合内部对象创建连接
        collectionModel.add(link); //添加链接
        return collectionModel;
    }
    @GetMapping("{id}")
    public EntityModel<PopInStock> popInStock(@PathVariable int id){
        Link link = links.linkToItemResource(PopInStock.class,id)
                .withRel("popInStock"); //给对象创建连接
        EntityModel<PopInStock> entityModel = new EntityModel<>(popInStockRepository.findById(id));
        entityModel.add(link); //添加链接
        return entityModel;
    }
}
