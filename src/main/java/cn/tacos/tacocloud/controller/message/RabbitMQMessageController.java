package cn.tacos.tacocloud.controller.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.message.RabbitMQConsumer;
import cn.tacos.tacocloud.message.RabbitMQProducer;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rabbit")
public class RabbitMQMessageController {
    @Autowired
    private JpaPopInStockRepository jpaPopInStockRepository;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    @Autowired
    private RabbitMQConsumer rabbitMQConsumer;
    @GetMapping("/send")
    public ResponseEntity<String> send(){
        PopInStock popInStock = jpaPopInStockRepository.findById(5);
        //rabbitMQProducer.sendPopInStock(popInStock);
        rabbitMQProducer.sendPopInStockConvert(popInStock);
        return ResponseEntity.ok("success");
    }
    @GetMapping("/receive")
    public ResponseEntity<PopInStock> receive(){
        return ResponseEntity.ok(rabbitMQConsumer.receivePopInStockConvert());
    }

}
