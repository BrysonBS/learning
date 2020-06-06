package cn.tacos.tacocloud.domain.assembler;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Relation(value = "pop",collectionRelation = "pops") //重命名生成的json字段名
public class PopInStockModel extends RepresentationModel<PopInStockModel> {
    private String code;
    private String name;

    //提供一个构造函数,设置spring调用此构造函数创建model对象,可以按自己需要进行赋值
    public PopInStockModel(PopInStock popInStock) {
        this.code = popInStock.getCode();
        this.name = popInStock.getName();
    }
}
