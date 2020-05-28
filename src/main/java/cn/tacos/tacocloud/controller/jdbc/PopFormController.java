package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.PopForm;
import cn.tacos.tacocloud.repository.jdbc.JdbcPopFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/popForm")
public class PopFormController {
    private JdbcPopFormRepository jdbcPopFormRepository;
    @Autowired
    public void setJdbcPopFormRepository(JdbcPopFormRepository jdbcPopFormRepository) {
        this.jdbcPopFormRepository = jdbcPopFormRepository;
    }

    //get请求时展示表单申请页面
    @GetMapping
    public String show(){
        return "popForm";
    }
    //post提交时保存申请数据
    @PostMapping
    @ResponseBody
    public String process(@RequestBody PopForm popForm){
        jdbcPopFormRepository.save(popForm);
        return "{\"success\":\"true\"}"; //返回json字符串
    }
}
