package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal findByIdAndUserId(Integer id, int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

//    @EntityGraph(value = "mealWithUser", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id =:id ORDER BY m.dateTime DESC")
    List<Meal> findAllWithUser(@Param("id") int userId);

    List<Meal> findByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime from, LocalDateTime to);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id")int id,@Param("userId") int userId);

    Meal findFirstByUserId(int userId);
}
