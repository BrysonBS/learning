package cn.tacos.tacocloud.controller.jpa;

import cn.tacos.tacocloud.domain.jpa.PopForm;
import cn.tacos.tacocloud.repository.jpa.JpaPopFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller(value = "jpaPopFormController")
@RequestMapping(value = "/popFormJpa")
public class PopFormController {
    private JpaPopFormRepository jpaPopFormRepository;
    @Autowired
    public void setJpaPopFormRepository(JpaPopFormRepository jpaPopFormRepository) {
        this.jpaPopFormRepository = jpaPopFormRepository;
    }

    @GetMapping
    public String show(){
        return "jpa/popForm";
    }
    @PostMapping()
    @ResponseBody
    public String process(@RequestBody PopForm popForm){
        System.out.println(popForm);
        jpaPopFormRepository.save(popForm);
        return "{\"success\":\"true\"}";
    }
    @PostMapping("/get")
    @ResponseBody
    public String findPopForm(){
        List<PopForm> list;
        //list = jpaPopFormRepository.findPopFormsByCompanyAndAndApplyDateBetween("abc",
        //        LocalDate.of(2020,5,5),
        //        LocalDate.of(2020,5,6)
        //        );
        //list = jpaPopFormRepository.readPopFormsByNameInOrderByApplyDate("1","2","3");
        list = jpaPopFormRepository.getPopForms("1");
        list.stream().forEach(System.out::println);
        System.out.println("--------------------------");
        System.out.println(jpaPopFormRepository.getPopForm(1));
        System.out.println(jpaPopFormRepository.getNativePopForm(1));
        return "{\"success\":\"true\"}";
    }
}
