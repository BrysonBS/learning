package cn.tacos.tacocloud.domain.jpa;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Data
@Table("POP_IN_STOCK")
public class PopInStock implements Serializable{
    @PrimaryKey
    private Integer id;
    @Column("TASKID")
    private Integer task;
    @Column("POP_CODE")
    private String code;
    @Column("POP_NAME")
    private String name;
    private String version;
    private String brand;
    @Column("IN_STOCK_COUNT")
    @JsonProperty(value = "count")
    private Double inStockCount;
    private String comment;
}
