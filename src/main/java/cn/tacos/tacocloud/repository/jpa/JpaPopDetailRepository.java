package cn.tacos.tacocloud.repository.jpa;

import cn.tacos.tacocloud.domain.jpa.PopDetail;
import org.springframework.data.repository.CrudRepository;

public interface JpaPopDetailRepository extends CrudRepository<PopDetail,Integer> {
}
