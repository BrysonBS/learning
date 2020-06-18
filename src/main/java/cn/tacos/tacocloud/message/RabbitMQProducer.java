package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//发送Message
@Component
public class RabbitMQProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //使用默认消息转换器
    public void sendPopInStock(PopInStock popInStock){
        /** 1. 使用默认的routing-key **/
        //给message中增加额外信息,无需添加信息时使用空对象即可
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("description","pop");
        //使用默认的MessageConverter: SimpleMessageConverter 将对象转换为Message
        Message message = rabbitTemplate.getMessageConverter().toMessage(popInStock,messageProperties);
        //不指定routing-key进行发送,此时使用配置中的routing-key: popInStock.queue
        rabbitTemplate.send(message);

        /** 2.使用指定的routing-key发送 **/
        //rabbitTemplate.send("popInStock.queue",message);
        /** 3. 使用默认的MessageConverter: SimpleMessageConverter 将对象转换为message发送 **/
        //rabbitTemplate.convertAndSend(popInStock);
    }

    /** 使用指定的MessageConverter: Jackson2JsonMessageConverter进行转换 **/
    //需要在Configuration配置类中配置Jackson2JsonMessageConverter的bean
    public void sendPopInStockConvert(PopInStock popInStock){
        rabbitTemplate.convertAndSend(popInStock,this::postProcessMessage);
    }
    private Message postProcessMessage(Message message) throws AmqpException {
        /********** 发送前为Message增加额外信息 ************/
        MessageProperties messageProperties = message.getMessageProperties();
        messageProperties.setHeader("description","pop");
        return message;
    }
}
