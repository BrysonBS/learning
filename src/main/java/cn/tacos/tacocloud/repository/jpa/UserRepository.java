package cn.tacos.tacocloud.repository.jpa;

import cn.tacos.tacocloud.domain.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
}
