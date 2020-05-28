package cn.tacos.tacocloud.controller.jdbc;

import cn.tacos.tacocloud.domain.jdbc.PopApply;
import cn.tacos.tacocloud.domain.jdbc.PopApplySon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/popApply")
public class PopApplyController {
    private ObjectMapper objectMapper;
    public PopApplyController() {
        objectMapper = new ObjectMapper();
    }

    //get请求时展示表单申请页面
    @GetMapping
    public String show(){
        return "popApply";
    }

    //post提交时保存申请数据
    @PostMapping
    @ResponseBody
    public String process(@RequestBody String formJson){
        //System.out.println(formJson);
        try {
            PopApply popApply = objectMapper.readValue(formJson, PopApply.class);
            List<PopApplySon>  list = objectMapper.readValue(
                    objectMapper.readTree(formJson).get("son").toString(),
                    new TypeReference<>() {});
            System.out.println(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{\"success\":\"true\"}"; //返回json字符串
    }
}
