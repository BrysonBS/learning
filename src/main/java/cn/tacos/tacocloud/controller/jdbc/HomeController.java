package cn.tacos.tacocloud.controller.jdbc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "jdbc/home";
    }

    @PostMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }
}
