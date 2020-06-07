package cn.tacos.tacocloud.domain.assembler;

import cn.tacos.tacocloud.controller.rest.PopController;
import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;


public class PopInStockAssembler extends RepresentationModelAssemblerSupport<PopInStock, PopInStockModel> {
    public PopInStockAssembler() {
        super(PopController.class,PopInStockModel.class);
    }


    //重写: 将对象转换为model对象
    @Override
    public PopInStockModel toModel(PopInStock entity) {
        return createModelWithId(entity.getId(),entity);
    }

    //重写此方法则使用此创建Model对象,默认使用无参构造函数创建
    @Override
    protected PopInStockModel instantiateModel(PopInStock entity) {
        return new PopInStockModel(entity);
    }
}
