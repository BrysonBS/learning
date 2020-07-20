package cn.tacos.tacocloud.repository.reactive;

import cn.tacos.tacocloud.domain.jpa.PopInStock;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveJpaPopRepository extends ReactiveCrudRepository<PopInStock,Integer> {
}
