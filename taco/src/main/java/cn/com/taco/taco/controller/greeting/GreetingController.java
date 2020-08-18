package cn.com.taco.taco.controller.greeting;

import cn.com.taco.taco.configuration.GreetingProps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/greet")
public class GreetingController {
    private final GreetingProps props;
    public GreetingController(GreetingProps props) {
        this.props = props;
    }
    @GetMapping("/hello")
    @ResponseBody
    public String message() {
        return props.getMessage();
    }
}
