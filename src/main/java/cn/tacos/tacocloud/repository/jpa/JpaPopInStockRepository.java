package cn.tacos.tacocloud.repository.jpa;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaPopInStockRepository extends CrudRepository<PopInStock,Integer> {
    List<PopInStock> findPopInStocksByOrderByTaskDesc(Pageable pageable);
}
