package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import javax.persistence.Access;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    User findOne(Integer id);

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
    List<User> findAll();

    User getByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userMeal WHERE u.id=:id")
//    @EntityGraph(value = "userWithMeal", type = EntityGraph.EntityGraphType.FETCH)
    User findOneWithMeal(@Param("id") Integer id);
}