package cn.tacos.tacocloud.repository.jpa;

import cn.tacos.tacocloud.domain.jpa.PopForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaPopFormRepository extends CrudRepository<PopForm,Integer> {
    //使用命名约定(naming convention)
    List<PopForm> findPopFormsByCompanyAndAndApplyDateBetween(String name, LocalDate start,LocalDate end);
    List<PopForm> readPopFormsByNameInOrderByApplyDate(String... name);

    //使用@Query
    @Query("from PopForm p where p.name = ?1")
    List<PopForm> getPopForms(String name);
    //使用@Param注入参数
    @Query("from PopForm p where p.id = :id")
    PopForm getPopForm(@Param("id") int id);
    //使用原生的SQL
    @Query(value = "SELECT * FROM POP_APPLY AS a WHERE a.id = ?1",nativeQuery = true)
    PopForm getNativePopForm(int id);
}
