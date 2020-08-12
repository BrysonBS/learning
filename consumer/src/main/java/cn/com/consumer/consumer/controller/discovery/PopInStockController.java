package cn.com.consumer.consumer.controller.discovery;

import cn.com.consumer.consumer.domain.discovery.PopInStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("pop")
@ResponseBody
public class PopInStockController {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    @GetMapping("/{id}")
    public PopInStock getById(@PathVariable int id){
        return restTemplate.getForObject(
                "http://taco/pop/get/{id}",
                PopInStock.class,
                id
        );
    }
}
