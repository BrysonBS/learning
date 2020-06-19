package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {
    @KafkaListener(topics = "popInStock.topic")
    public void receive(ConsumerRecord<String,PopInStock> record){
        System.out.println("Kafka-receive: "+ record);
    }
}
