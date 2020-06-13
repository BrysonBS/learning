package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 发送message
 */
@Component
public class Producer {
    private JmsTemplate jmsTemplate;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    //使用默认的destination
    public void sendPopInStock(PopInStock popInStock) {
        jmsTemplate.send(session -> session.createObjectMessage(popInStock));
    }
    //使用自定义的destination
    @Autowired
    private Destination popInStockQueue;

    public void sendPopInStockByDestination(PopInStock popInStock) {
        jmsTemplate.send(popInStockQueue, session -> session.createObjectMessage(popInStock));
    }
    //直接指定destination
    public void sendPopInStockOfDestination(PopInStock popInStock) {
        jmsTemplate.send("taco.popInStock.queue", session -> session.createObjectMessage(popInStock));
    }
    //使用默认的SimpleMessageConverter将对象转换为Message再发送
    public void sendPopInStockConvert(PopInStock popInStock){
        jmsTemplate.convertAndSend("taco.popInStock.queue",popInStock);
    }
    //使用MappingJackson2MessageConverter消息转换器
    public void sendPopInStockConvert1(PopInStock popInStock){

    }
}
