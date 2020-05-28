package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.Refund;
import cn.tacos.tacocloud.repository.jdbc.JdbcRefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayDeque;
import java.util.Queue;

@Controller
@RequestMapping("/refund")
public class RefundController {
    private JdbcRefundRepository jdbcRefundRepository;
    @Autowired
    public void setJdbcRefundRepository(JdbcRefundRepository jdbcRefundRepository) {
        this.jdbcRefundRepository = jdbcRefundRepository;
    }
    @GetMapping
    public String show(Refund refund, Model model){
        Queue<Refund> queue = new ArrayDeque<>();
        jdbcRefundRepository.findAll().forEach(queue::offer);
        model.addAttribute("list",queue);
        return "refund";
    }
    @PostMapping
    public String submit(@Valid Refund refund, Errors errors,Model model){
        System.out.println("save_start");
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                System.out.println(error.toString());
            }
            return "refund";
        }
        //保存
        System.out.println("save");
        System.out.println(jdbcRefundRepository.save(refund));
        return "refund";
    }
}
