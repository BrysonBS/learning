package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.Ingredient;
import cn.tacos.tacocloud.domain.jdbc.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/design")
public class DesignTacoController {
    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> list = Arrays.asList(
                new Ingredient("1","RED", Ingredient.Color.RED),
                new Ingredient("2","YELLOW", Ingredient.Color.YELLOW),
                new Ingredient("3","BLUE", Ingredient.Color.BLUE)
        );
        model.addAttribute("list",list);
        model.addAttribute("color","PINK");
        return "design";
    }
    @PostMapping
    public String getDesignForm(@Valid Order order, Errors errors,Model model){
        model.addAttribute("order",order);
        if(errors.hasErrors()){
            //System.out.println(errors.toString());
            return "design";
        }
        System.out.println(order.toString());
        return "design";
    }

}
