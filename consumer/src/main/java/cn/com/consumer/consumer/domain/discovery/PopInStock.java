package cn.com.consumer.consumer.domain.discovery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PopInStock implements Serializable {
    private Integer id;
    private Integer task;
    private String code;
    private String name;
    private String version;
    private String brand;
    @JsonProperty(value = "count")
    private Double inStockCount;
    private String comment;
}
