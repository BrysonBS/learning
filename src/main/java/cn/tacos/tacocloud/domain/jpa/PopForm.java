package cn.tacos.tacocloud.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "POP_APPLY")
public class PopForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "申请人不能为空")
    private String name;
    private String company;
    //@JsonIgnore
    private LocalDate applyDate;
    @JsonProperty(value = "son")
    @OneToMany(targetEntity = PopDetail.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "POP_APPLY_ID")
    private List<PopDetail> popDetails;
    @PrePersist
    void applyDateAt(){
        this.applyDate = LocalDate.now();
    }
}
