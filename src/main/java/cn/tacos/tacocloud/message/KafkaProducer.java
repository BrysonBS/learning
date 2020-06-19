package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String,PopInStock> kafkaTemplate;
    public void send(PopInStock popInStock){
        MessageConverter converter = kafkaTemplate.getMessageConverter();
        //kafkaTemplate.sendDefault(popInStock);
        kafkaTemplate.send("popInStock.topic",popInStock);
    }
}
