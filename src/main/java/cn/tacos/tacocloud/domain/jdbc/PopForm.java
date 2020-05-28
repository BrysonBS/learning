package cn.tacos.tacocloud.domain.jdbc;

import cn.tacos.tacocloud.jackson.LocalDateDeSerializer;
import cn.tacos.tacocloud.jackson.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
public class PopForm {
    private Integer id;
    @NotBlank(message = "申请人不能为空")
    private String name;
    private String company;

    private LocalDate applyDate;
    @JsonProperty(value = "son")
    private List<PopApplySon> popApplySons;

    @JsonGetter(value = "APPLY_DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getApplyDate() {
        return applyDate;
    }
    @JsonSetter(value = "applyDate")
    @JsonDeserialize(using = LocalDateDeSerializer.class)
    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }
}
