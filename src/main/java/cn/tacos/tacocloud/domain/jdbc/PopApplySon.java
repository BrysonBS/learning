package cn.tacos.tacocloud.domain.jdbc;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class PopApplySon {
    @JsonIgnore
    private Integer id;
    private Integer popApplyId;
    private String goodsCode;
    private Integer amount;

    @JsonGetter(value = "POP_APPLY_ID")
    public Integer getPopApplyId() {
        return popApplyId;
    }
    @JsonGetter(value = "GOODS_CODE")
    public String getGoodsCode() {
        return goodsCode;
    }
    @JsonGetter(value = "amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonSetter(value = "goodsCode")
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    @JsonSetter(value = "totalAmount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
