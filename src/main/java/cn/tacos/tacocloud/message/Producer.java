package cn.tacos.tacocloud.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

//发送Message
@Component
public class Producer {
    private JmsTemplate jmsTemplate;
    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /** 1.使用默认的消息转换器: SimpleMessageConverter
     * 注意: 对象类型必须实现Serializable接口  **/
    //使用默认的destination
    public void sendPopInStock(PopInStock popInStock) {
        jmsTemplate.send(session -> session.createObjectMessage(popInStock));
    }
    //使用自定义的destination
    //@Autowired
    private Destination popInStockQueue;
    public void sendPopInStockByDestination(PopInStock popInStock) {
        jmsTemplate.send(popInStockQueue, session -> session.createObjectMessage(popInStock));
    }
    //直接指定destination
    public void sendPopInStockOfDestination(PopInStock popInStock) {
        jmsTemplate.send("taco.popInStock.queue", session -> session.createObjectMessage(popInStock));
    }

    /** 使用指定的消息转换器 **/
    //使用MappingJackson2MessageConverter消息转换器: 对象类型无需实现Serializable接口
    public void sendPopInStockConvert(PopInStock popInStock){
        jmsTemplate.convertAndSend("taco.popInStock.queue", popInStock,this::postProcessMessage);
    }
    private Message postProcessMessage(Message message) throws JMSException {
        //message为使用转换器转换过的消息
        //发送前给message增加额外的信息
        message.setStringProperty("description", "pop");
        System.out.println(message);
        return message;
    }
}
