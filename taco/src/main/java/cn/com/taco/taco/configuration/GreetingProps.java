package cn.com.taco.taco.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="greeting")
@Component
@Data
public class GreetingProps {
    private String message;
}
