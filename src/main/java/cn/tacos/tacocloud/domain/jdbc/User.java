package cn.tacos.tacocloud.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class User {
    @NotBlank(message = "用户名不能为空")
    private String name;
    @Size(min = 11,max = 11,message = "手机长度必须为11位")
    private String phone;
    @Size(min = 6,max = 12,message = "密码长度6~12")
    private String password;
}
