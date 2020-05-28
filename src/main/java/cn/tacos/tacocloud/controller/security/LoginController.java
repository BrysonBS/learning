package cn.tacos.tacocloud.controller.security;

import cn.tacos.tacocloud.domain.security.User;
import cn.tacos.tacocloud.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;

/*@Configuration
public class LoginController implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("security/login");
        registry.addViewController("/success").setViewName("security/success");
    }
}*/

@Controller
public class LoginController{
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/login")
    public String login(){
        return "security/login";
    }
    //@RequestMapping("/success")
    public String success(Principal principal, SessionStatus sessionStatus){
        //通过Principal对象获取到验证用户名,然后再根据用户名从数据库中查找该用户
        User user = userRepository.findByUsername(principal.getName());
        System.out.println(user);
        System.out.println(sessionStatus.isComplete());
        return "security/success";
    }

    //@RequestMapping("/success")
    public String success1(Authentication authentication){
        //通过 authentication 对象获取到验证用户
        User user = (User) authentication.getPrincipal();
        System.out.println(user);
        return "security/success";
    }

    //@RequestMapping("/success")
    public String success2(@AuthenticationPrincipal User user){
        //通过 @AuthenticationPrincipal 注解获取到验证用户
        System.out.println(user);
        return "security/success";
    }

    @RequestMapping("/success")
    public String success3(){
        //通过SecurityContext对象获取Authentication对象,然后再获取到验证用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        System.out.println(user);
        System.out.println(3);
        return "security/success";
    }
}
