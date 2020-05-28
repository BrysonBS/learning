package cn.tacos.tacocloud.repository.jdbc;

import cn.tacos.tacocloud.domain.jdbc.User;
import cn.tacos.tacocloud.repository.impl.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcUserRepository implements BaseRepository<User> {
    private JdbcTemplate template;
    @Autowired
    public JdbcUserRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Iterable<User> findAll() {
        String sql = "select top 5 Account,DisplayName,OfficePhone from BPMSysUsers";
        return template.query(sql, this::MapRowToUser);
    }

    @Override
    public User findOne(String id) {
        String sql = "select Account,DisplayName,OfficePhone from BPMSysUsers where Account = ?";
        return template.queryForObject(sql,this::MapRowToUser,id);
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    private User MapRowToUser(ResultSet rs, int rows) throws SQLException{
        System.out.println(rows);
        return new User(
                rs.getString("Account"),
                rs.getString("OfficePhone"),
                rs.getString("DisplayName")
        );
    }

}
