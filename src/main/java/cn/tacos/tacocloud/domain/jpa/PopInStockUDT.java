package cn.tacos.tacocloud.domain.jpa;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType("popInStock")
public class PopInStockUDT{
    private String code;
    private String name;
}
