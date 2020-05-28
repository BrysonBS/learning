package cn.tacos.tacocloud.configuration.holder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@ConfigurationProperties(prefix = "stock")
@Validated //数据校验注解
public class ViewProps {
    @Max(value = 25,message = "必须在5~25之间")
    @Min(value = 5,message = "必须在5~25之间")
    private int pageSize = 1;
}
