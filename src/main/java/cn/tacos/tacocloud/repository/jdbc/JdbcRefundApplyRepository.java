package cn.tacos.tacocloud.repository.jdbc;

import cn.tacos.tacocloud.domain.jdbc.Refund;
import cn.tacos.tacocloud.domain.jdbc.RefundApply;
import cn.tacos.tacocloud.domain.jdbc.RefundDetail;
import cn.tacos.tacocloud.repository.impl.BaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcRefundApplyRepository implements BaseRepository<RefundApply> {
    private JdbcTemplate template;
    private SimpleJdbcInsert refundInsert;
    private SimpleJdbcInsert refundDetailInsert;
    private ObjectMapper objectMapper;
    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Iterable<RefundApply> findAll() {
        return null;
    }

    @Override
    public RefundApply findOne(String id) {
        return null;
    }

    @Override
    public boolean save(RefundApply apply) {
        refundInsert = new SimpleJdbcInsert(template)
                .withTableName("NBL_REFUND") //要插入的表名
                .usingColumns("Company","ApplicationDate","ApplicantName","RefundNo","RefundUnit")//要插入的列
                .usingGeneratedKeyColumns("TaskID"); //设置主键执行executeAndReturnKey会返回此键
        refundDetailInsert = new SimpleJdbcInsert(template)
                .withTableName("NBL_REFUND_SON")
                .usingColumns("TaskID","GoodsCode","BatchNumber","TotalAmount");
        objectMapper = new ObjectMapper();
        //先保存refund
        int taskId = saveRefund(apply.getRefund());
        //再保存refundDetail
        for(RefundDetail d : apply.getDetails()){
            d.setTaskId(taskId);
            saveRefundDetail(d);
        }
        return true;
    }
    private int saveRefund(Refund refund){
        Map<String,Object> values = objectMapper.convertValue(refund, Map.class);
        System.out.println(values.toString());
        return refundInsert.executeAndReturnKey(values).intValue();
    }
    private void saveRefundDetail(RefundDetail detail){
        Map<String,Object> values = objectMapper.convertValue(detail,Map.class);
        refundDetailInsert.execute(values);
    }
}
