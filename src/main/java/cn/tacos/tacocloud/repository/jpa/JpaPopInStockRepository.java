package cn.tacos.tacocloud.repository.jpa;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface JpaPopInStockRepository extends CrudRepository<PopInStock,Integer> {
    List<PopInStock> findPopInStocksByOrderByTaskDesc(Pageable pageable);
    PopInStock findById(int id);
    List<PopInStock> findAll(Pageable pageable);
}
