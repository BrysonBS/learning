package cn.tacos.tacocloud.repository.jdbc;

import cn.tacos.tacocloud.domain.jdbc.PopApplySon;
import cn.tacos.tacocloud.domain.jdbc.PopForm;
import cn.tacos.tacocloud.repository.impl.BaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcPopFormRepository implements BaseRepository<PopForm> {
    private JdbcTemplate template;
    private SimpleJdbcInsert popInsert;
    private SimpleJdbcInsert popSonInsert;
    private ObjectMapper objectMapper;

    //依赖注入
    @Autowired
    public JdbcPopFormRepository(JdbcTemplate template) {
        this.template = template;
        objectMapper = new ObjectMapper();
    }

    @Override
    public Iterable<PopForm> findAll() {
        return null;
    }
    @Override
    public PopForm findOne(String id) {
        return null;
    }

    @Override
    public boolean save(PopForm popForm) {
        System.out.println(popForm);
        popInsert = new SimpleJdbcInsert(template)
                .withTableName("POP_APPLY") //要插入的表
                .usingColumns("NAME","COMPANY","APPLY_DATE") //要插入的字段
                .usingGeneratedKeyColumns("ID"); //设置主键,执行executeAndReturnKey会返回此键
        popSonInsert = new SimpleJdbcInsert(template)
                .withTableName("POP_APPLY_SON")
                .usingColumns("POP_APPLY_ID","GOODS_CODE","AMOUNT");
        //先保存主表
        int id = savePop(popForm);
        //保存明细表
        popForm.getPopApplySons().stream().forEach(
                e -> {
                    e.setPopApplyId(id);
                    savePopSon(e);
                }
        );
        return true;
    }

    private int savePop(PopForm popForm){
        Map<String,Object> values = objectMapper.convertValue(popForm,Map.class);
        System.out.println(values);
        return popInsert.executeAndReturnKey(values).intValue();
    }
    private void savePopSon(PopApplySon son){
        Map<String,Object> values = objectMapper.convertValue(son,Map.class);
        System.out.println(values);
        popSonInsert.execute(values);
    }
}
