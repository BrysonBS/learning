package cn.tacos.tacocloud.controller.message;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

    public void embedBroker(){

    }
}
