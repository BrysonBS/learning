package cn.tacos.tacocloud.domain.jpa;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;
@Data
@Table("POP_FROM")
public class PopFrom {
    @PrimaryKey
    private Integer id;
    @Column("CREATE_BY")
    private String createBy;
    @Column("CREATE_DATE")
    private LocalDate createDate;
    //使用自定义类型只从PopInStock中取部分数据
    private List<PopInStockUDT> pops;
}
