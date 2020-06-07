package cn.tacos.tacocloud.controller.rest;

import cn.tacos.tacocloud.domain.assembler.PopInStockAssembler;
import cn.tacos.tacocloud.domain.assembler.PopInStockModel;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RepositoryRestController
public class PopDataRestController {
    @Autowired
    private JpaPopInStockDataRestRepository popRepository;

    private EntityLinks entityLinks;
    @Autowired
    public void setEntityLinks(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    //注意: /popInStocks前缀必须
    @RequestMapping(method = RequestMethod.GET,path = "/popInStocks/recent",produces = "application/hal+json") //设置请求和响应类型
    public ResponseEntity<CollectionModel<PopInStockModel>> recents(){
        PageRequest pageRequest = PageRequest.of(0,5);
        List<PopInStock> list = popRepository.findPopInStocksByOrderByTaskDesc(pageRequest);
        CollectionModel<PopInStockModel> collections = new PopInStockAssembler().toCollectionModel(list);
        collections.add(
                entityLinks.linkFor(PopInStock.class)
                        .slash("/recent")
                        .withRel("recents"));
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
}
