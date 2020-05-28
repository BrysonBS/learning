package cn.tacos.tacocloud.controller.jpa;

import cn.tacos.tacocloud.configuration.holder.ViewProps;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/stock")
//@ConfigurationProperties(prefix = "stock") //引入前缀为stock的配置属性 stock.pageSize = 15
public class PopStockController {
    private JpaPopInStockRepository popInStockRepository;
    @Autowired
    public void setPopInStockRepository(JpaPopInStockRepository popInStockRepository) {
        this.popInStockRepository = popInStockRepository;
    }
/*    //会根据自定义的配置属性自动设置pageSize的值
    private int pageSize = 1; //默认值为1
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }*/

    private ViewProps viewProps;
    @Autowired
    public void setViewProps(ViewProps viewProps) {
        this.viewProps = viewProps;
    }

    @GetMapping
    public String show(){
        return "jpa/popInStock";
    }

    @ResponseBody //list转json返回
    @PostMapping
    public String process() throws JsonProcessingException {
        //Pageable pageable = PageRequest.of(0,pageSize);
        Pageable pageable = PageRequest.of(0,viewProps.getPageSize());
        List<PopInStock> list = popInStockRepository.findPopInStocksByOrderByTaskDesc(pageable);
        return new ObjectMapper().writeValueAsString(list);
    }
}
