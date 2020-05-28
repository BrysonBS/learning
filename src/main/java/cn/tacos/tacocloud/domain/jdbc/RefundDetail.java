package cn.tacos.tacocloud.domain.jdbc;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefundDetail {
    private Integer TaskId;
    @NotBlank(message = "品名不能为空")
    private String goodsCode;
    private String batchNumber;
    private String totalAmount;
}
