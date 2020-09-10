package cn.com.taco.taco.controller.breaker;

import cn.com.taco.taco.domain.discovery.PopInStock;
import cn.com.taco.taco.repository.discovery.PopInStockRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/breaker")
public class CircuitBreakerController {
    @Autowired
    private PopInStockRepository popInStockRepository;
    @GetMapping("/{id}")
    @ResponseBody
    @HystrixCommand(
            fallbackMethod = "getDefaultPopInStock",
            commandProperties = {
                    @HystrixProperty(
                            name="execution.isolation.thread.timeoutInMilliseconds",
                            value="500")//超过500ms就会触发断路器
            }
    )

    public PopInStock getById(@PathVariable int id){
        return popInStockRepository.findById(id).get();
    }
    private PopInStock getDefaultPopInStock(int id){
        return popInStockRepository.findById(1).get();
    }
}
