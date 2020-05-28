package cn.tacos.tacocloud.domain.jdbc;

import cn.tacos.tacocloud.jackson.LocalDateDeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties({"son","id"})
public class PopApply {
    private Integer id;
    @NotBlank(message = "申请人不能为空")
    private String name;
    private String company;
    @JsonDeserialize(using = LocalDateDeSerializer.class)
    private LocalDate applyDate;
}
