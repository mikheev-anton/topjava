package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getOwner().getId()==userId){
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }

            repository.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean deleteByUser(int userId, int id) {
        Meal meal = getUserMeal(userId, id);

        if (meal!=null){
            repository.remove(id);
            return true;
        }

        return false;
    }

    @Override
    public Meal getByUser(int userId, int id) {
        return getUserMeal(userId, id);
    }

    @Override
    public Collection<Meal> filter(LocalTime startTime, LocalTime endTime, int userId) {
        return getAllByUser(userId).stream()
                .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(),startTime, endTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> filter(LocalDate startDate, LocalDate endDate, int userId) {
        return getAllByUser(userId).stream()
                .filter(meal -> TimeUtil.isBetweenDate(meal.getDate(),startDate,endDate))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Collection<Meal> getAllByUser(int userId) {
        return getAll().stream()
                .filter(meal -> meal.getOwner().getId()==userId)
                .collect(Collectors.toList());
    }

    private Meal getUserMeal(int userId, int id){
        Meal meal = repository.get(id);

        if (meal!=null&&meal.getOwner().getId()==userId)
            return meal;

        else return null;
    }
}

