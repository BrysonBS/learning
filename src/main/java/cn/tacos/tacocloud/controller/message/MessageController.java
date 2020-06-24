package cn.tacos.tacocloud.controller.message;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import cn.tacos.tacocloud.integration.message.Consumer;
import cn.tacos.tacocloud.integration.message.Producer;
import cn.tacos.tacocloud.repository.jpa.JpaPopInStockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.jms.JMSException;

@Controller
@RequestMapping("/message")
public class MessageController {
    private JmsTemplate jmsTemplate;
    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    @GetMapping("/sendText")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void send(){
        //Destination destination = new ActiveMQQueue("localhost:61616");
        jmsTemplate.send(session -> session.createTextMessage("sendText"));
    }
    @GetMapping("/receiveText")
    @ResponseBody
    public String receive() throws JMSException {
        return jmsTemplate.receive().getBody(String.class);
    }

    @Autowired
    private JpaPopInStockRepository jpaPopInStockRepository;
    @Autowired
    private Producer producer;
    @GetMapping("/sendPop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendPop() throws JsonProcessingException {
        PopInStock popInStock = jpaPopInStockRepository.findById(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(popInStock);
        System.out.println(string);
        producer.sendPopInStockConvert(popInStock);
    }

    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private Consumer consumer;
    @GetMapping("receivePop")
    public ResponseEntity<PopInStock> receivePop() throws JMSException {
        PopInStock popInStock = consumer.receivePopInStockConvert();
        System.out.println(popInStock);
        return ResponseEntity.ok(popInStock);
    }

}
