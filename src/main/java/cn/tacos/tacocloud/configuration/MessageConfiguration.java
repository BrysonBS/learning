package cn.tacos.tacocloud.configuration;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.Destination;
import java.util.Map;


@Configuration
public class MessageConfiguration {
    @Bean
    public MessageConverter amqpMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    //@Bean
    public Destination destination(){
        return new ActiveMQQueue("popInStockQueue");
    }
    //@Bean
    public MappingJackson2MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        //
        /** 默认: 使用完整类名作为标记 **/
        //messageConverter.setTypeIdPropertyName("_typeId");
        //输出: ActiveMQMessage[null]:PERSISTENT/ClientMessageImpl[messageID=0, durable=true, address=null,userID=null,properties=TypedProperties[_typeId=cn.tacos.tacocloud.domain.jpa.PopInStock]]

        /** 重新命名_typeId的值(仅对指定的类型更改) **/
        messageConverter.setTypeIdPropertyName("_typeId");
        messageConverter.setTypeIdMappings(Map.of("popInStock", PopInStock.class));
        //输出: ActiveMQMessage[null]:PERSISTENT/ClientMessageImpl[messageID=0, durable=true, address=null,userID=null,properties=TypedProperties[_typeId=popInStock]]
        return messageConverter;
    }
}
