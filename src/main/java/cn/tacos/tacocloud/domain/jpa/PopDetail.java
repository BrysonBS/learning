package cn.tacos.tacocloud.domain.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;

@Data
@Entity
@Table(name = "POP_APPLY_SON")
public class PopDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String goodsCode;
    @JsonProperty(value = "totalAmount")
    @Column(name = "totalAmount")
    private Integer amount;
}
