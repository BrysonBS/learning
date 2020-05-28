package cn.tacos.tacocloud.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Refund {
    private String taskId;
    private String company;
    private LocalDate applyDate;
    @NotBlank(message = "名字不能为空")
    private String applyName;
    @NotNull
    private String refundNo;
    private String refundUnit;
}
