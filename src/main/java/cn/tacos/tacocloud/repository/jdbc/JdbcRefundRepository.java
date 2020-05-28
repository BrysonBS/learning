package cn.tacos.tacocloud.repository.jdbc;

import cn.tacos.tacocloud.domain.jdbc.Refund;
import cn.tacos.tacocloud.repository.impl.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcRefundRepository implements BaseRepository<Refund> {
    private JdbcTemplate template;
    @Autowired
    public JdbcRefundRepository(JdbcTemplate template) {
        this.template = template;
    }

    String SQL = "select TaskID,Company,ApplicationDate,ApplicantName,RefundNo,RefundUnit from NBL_REFUND";
    @Override
    public Iterable<Refund> findAll() {
        String SQL_ALL = SQL+" ORDER BY TaskID OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY";
        return template.query(SQL_ALL,this::mapRow);
    }

    @Override
    public Refund findOne(String taskId) {
        String SQL_ONE = SQL + " where TaskID = ?";
        return template.queryForObject(SQL_ONE,this::mapRow,taskId);
    }

    @Override
    public boolean save(Refund refund) {
        String SQL_SAVE = "INSERT INTO NBL_REFUND(Company,ApplicationDate,ApplicantName,RefundNo,RefundUnit) VALUES(?,?,?,?,?)";
        int row = template.update(SQL_SAVE,
                refund.getCompany(),
                refund.getApplyDate(),
                refund.getApplyName(),
                refund.getRefundNo(),
                refund.getRefundUnit());
        return row == 1;
    }

    public Refund mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Refund(
                resultSet.getInt("TaskID")+"",
                resultSet.getString("Company"),
                resultSet.getDate("ApplicationDate").toLocalDate(),
                resultSet.getString("ApplicantName"),
                resultSet.getString("RefundNo"),
                resultSet.getString("RefundUnit")
        );
    }
}
