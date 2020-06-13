package cn.tacos.tacocloud.configuration;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import javax.jms.Destination;


@Configuration
public class MessageConfiguration {
    @Bean
    public Destination destination(){
        return new ActiveMQQueue("popInStockQueue");
    }
/*    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(){

    }*/
}
