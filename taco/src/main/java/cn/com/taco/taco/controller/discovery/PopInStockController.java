package cn.com.taco.taco.controller.discovery;

import cn.com.taco.taco.domain.discovery.PopInStock;
import cn.com.taco.taco.repository.discovery.PopInStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/pop")
public class PopInStockController {
    @Autowired
    private PopInStockRepository popInStockRepository;
    @GetMapping("/all")
    @ResponseBody
    public List<PopInStock> getAll(){
        return popInStockRepository.findAll();
    }
    @GetMapping("/get/{id}")
    @ResponseBody
    public PopInStock getById(@PathVariable int id){
        return popInStockRepository.findById(id).orElse(null);
    }
}
