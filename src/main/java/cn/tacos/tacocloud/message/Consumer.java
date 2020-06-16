package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * 消费者: 接收消息
 */
@Component
public class Consumer {
    private JmsTemplate jmsTemplate;
    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;
    public PopInStock receivePopInStockConvert() throws JMSException {
        //return (PopInStock) jmsTemplate.receiveAndConvert("taco.popInStock.queue");
        //与下等价
        Message message = jmsTemplate.receive("taco.popInStock.queue");
        return (PopInStock) mappingJackson2MessageConverter.fromMessage(message);
    }
}
