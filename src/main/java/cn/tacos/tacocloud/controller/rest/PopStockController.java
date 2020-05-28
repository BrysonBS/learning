package cn.tacos.tacocloud.controller.rest;

import cn.tacos.tacocloud.configuration.holder.ViewProps;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "StockController")
@RequestMapping(path = "/pop" , produces = "application/json")
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

    @GetMapping(value = "/stock")
    @ResponseBody
    public String show() throws JsonProcessingException {
        List<PopInStock> list = popInStockRepository.findPopInStocksByOrderByTaskDesc(PageRequest.of(0,viewProps.getPageSize()));
        return new ObjectMapper().writeValueAsString(list);
    }
}
