package cn.com.taco.taco.repository.discovery;

import cn.com.taco.taco.domain.discovery.PopInStock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PopInStockRepository extends CrudRepository<PopInStock,Integer> {
    List<PopInStock> findAll();
}
