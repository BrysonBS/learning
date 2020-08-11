package cn.tacos.tacocloud.repository.reactive;

import cn.tacos.tacocloud.domain.jpa.PopFrom;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReactivePopFromRepository extends ReactiveCassandraRepository<PopFrom,Integer> {
    @AllowFiltering //由于cassandra默认情况下只允许根据主键过滤,如果想使用其他字段过滤则需要 allow filtering
    //等价于CQL: select * from pop_from where create_by = 'Tom1' allow filtering;
    Flux<PopFrom> getByCreateBy(String name);
}
