package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.RefundApply;
import cn.tacos.tacocloud.repository.jdbc.JdbcRefundApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/refundApply")
public class RefundApplyController {
    private JdbcRefundApplyRepository jdbcRefundApplyRepository;
    @Autowired
    public void setJdbcRefundApplyRepository(JdbcRefundApplyRepository jdbcRefundApplyRepository) {
        this.jdbcRefundApplyRepository = jdbcRefundApplyRepository;
    }

    //get请求时展示表单申请页面
    @GetMapping
    public String show(){
        return "refundApply";
    }

    //post提交时保存申请数据
    @PostMapping
    @ResponseBody
    public String process(@RequestBody RefundApply apply){
        jdbcRefundApplyRepository.save(apply);
        return "{\"success\":\"true\"}";
    }
}
