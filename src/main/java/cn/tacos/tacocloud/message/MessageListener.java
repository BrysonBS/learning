package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @JmsListener(destination = "taco.popInStock.queue")
    public void receive(PopInStock popInStock){
        System.out.println(1111);
        System.out.println(popInStock);
    }
}
