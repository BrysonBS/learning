package cn.tacos.tacocloud.repository.jpa;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

//@RepositoryRestResource(path="popInStocks",collectionResourceRel="pops",itemResourceRel="pop")
//@RepositoryRestResource(exported = false) 设置不公开此Repository
public interface JpaPopInStockDataRestRepository extends CrudRepository<PopInStock,Integer> {
    //@RestResource(path="recent",rel="recents") //可在方法上设置
    List<PopInStock> findPopInStocksByOrderByTaskDesc(Pageable pageable);
    @RestResource(exported = false)//设置不公开此方法
    PopInStock findById(int id);
}
