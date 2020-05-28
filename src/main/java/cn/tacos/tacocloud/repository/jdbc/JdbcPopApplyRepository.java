package cn.tacos.tacocloud.repository.jdbc;

import cn.tacos.tacocloud.repository.impl.BaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPopApplyRepository implements BaseRepository {
    private JdbcTemplate template;
    //插入数据时使用模版
    private SimpleJdbcInsert popApplyInsert;
    private SimpleJdbcInsert popApplySonInsert;
    private ObjectMapper objectMapper; //Jackson对象

    @Override
    public Iterable findAll() {
        return null;
    }

    @Override
    public Object findOne(String id) {
        return null;
    }

    @Override
    public boolean save(Object o) {
        return false;
    }
}
