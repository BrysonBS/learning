package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 消费者: 接收消息
 */
@Component
public class RabbitMQConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //接收消息
    public PopInStock receivePopInStock(){
        //Message message = rabbitTemplate.receive("popInStock.queue");
        //return (PopInStock) rabbitTemplate.getMessageConverter().fromMessage(message);

        ////System.out.println("Jackson2JsonMessageConverter:" + (rabbitTemplate.getMessageConverter() instanceof Jackson2JsonMessageConverter));
        //与上等价: 接收并转换消息
        return (PopInStock) rabbitTemplate.receiveAndConvert("popInStock.queue");
    }
    //接收消息并设置超时等待时间
    public PopInStock receivePopInStockTimeOut(){
        Message message = rabbitTemplate.receive("popInStock.queue",3000);
        if(message != null) return (PopInStock) rabbitTemplate.getMessageConverter().fromMessage(message);
        else return null;
    }
    //接收并转换消息
    public PopInStock receivePopInStockConvert(){
        //return (PopInStock) rabbitTemplate.receiveAndConvert("popInStock.queue");
        //同上传入new ParameterizedTypeReference<PopInStock>(){}无需强转
        return rabbitTemplate.receiveAndConvert("popInStock.queue", new ParameterizedTypeReference<>(){});
    }
}
