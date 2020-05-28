package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.User;
import cn.tacos.tacocloud.repository.jdbc.JdbcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/form")
public class UserController {
    private JdbcUserRepository jdbcUserRepository;
    @Autowired
    public void setJdbcUserRepository(JdbcUserRepository jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @GetMapping
    public String form(User user){
        return "form";
    }

    @PostMapping
    public String submit(@Valid User user, Errors result){
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                System.out.println(error.getDefaultMessage());
            }
            return "form";
        }
        //业务逻辑处理
        User u = jdbcUserRepository.findOne(user.getName());

        //jdbcUserRepository.findAll();
        System.out.println(u.getName()+","+u.getPassword());
        return "form";
    }
}
