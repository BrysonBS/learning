package cn.tacos.tacocloud.domain.jdbc;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;
import java.util.List;


@Data
public class Order {
    @NotNull
    @NotBlank(message = "不能为空")
    private String id;
    @Size(min = 3,message = "名称最小为三个字符")
    private String name;
/*    @CreditCardNumber(message = "不是有效信用卡号码")
    private String ccNumber;
    @Pattern(regexp = "(0|1(01*0)*1)*",message = "正则校验失败")
    private String ccExpression;
    @Digits(integer=3, fraction=0, message="验证整数部分为三个数字,小数部分为0个失败")
    private String digit;
    @Size(min = 1,message = "最少有一个元素")
    private List<Ingredient> ingredients;*/
}
