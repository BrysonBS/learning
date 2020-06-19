package cn.tacos.tacocloud.controller.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.message.KafkaProducer;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kafka")
public class KafkaMessageController {
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private JpaPopInStockRepository jpaPopInStockRepository;
    @GetMapping("/send")
    public ResponseEntity<String> show(){
        PopInStock popInStock = jpaPopInStockRepository.findById(2);
        kafkaProducer.send(popInStock);
        return ResponseEntity.ok("success");
    }
}
