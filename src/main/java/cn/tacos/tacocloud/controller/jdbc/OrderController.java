package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.Order;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * GET提交时获取页面
 * POST提交时会进行校验
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @GetMapping
    public String show(Order order){ //校验需要: order参数和post提交一致
        return "order";
    }

    /**
     * @Valid 表示提交时要校验order对象,具体校验规则在Order类中定义
     * 使用@Valid注解在表单提交后执行processOrder方法之前进行校验
     * errors对象保存校验的信息
     */
    @PostMapping
    public String get(@Valid Order order, Errors errors){
        if(errors.hasErrors()){
            return "order";
        }
        return "home";
    }
}
