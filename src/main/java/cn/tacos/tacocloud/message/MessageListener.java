package cn.tacos.tacocloud.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

//@Component
public class MessageListener {
    //@JmsListener(destination = "localhost")
    public void receive(String v){
        System.out.println(v);
    }
}
