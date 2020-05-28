package cn.tacos.tacocloud.domain.jpa;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "POP_IN_STOCK")
public class PopInStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "TASKID")
    private Integer task;
    @Column(name = "POP_CODE")
    private String code;
    @Column(name = "POP_NAME")
    private String name;
    private String version;
    private String brand;
    @Column(name = "IN_STOCK_COUNT")
    @JsonProperty(value = "count")
    private Double inStockCount;
    private String comment;
}
