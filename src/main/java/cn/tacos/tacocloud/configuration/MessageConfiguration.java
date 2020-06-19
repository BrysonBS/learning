package cn.tacos.tacocloud.configuration;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class MessageConfiguration {
    //@Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    //@Bean
    public Map<String,Object> consumerConfigs(){
        Map<String,Object> props = new HashMap<>();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "cn.tacos.tacocloud.domain.jpa");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "cn.tacos.tacocloud.domain.jpa.PopInStock");
        return props;
    }
}
