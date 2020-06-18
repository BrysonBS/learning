package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageListener {
    @RabbitListener(queues = "popInStock.queue")
    public void receive(PopInStock popInStock){
        //从queue: popInStock.queue中接收到PopInStock
        //...其他操作
        System.out.println("push model: " + popInStock);
    }
}
