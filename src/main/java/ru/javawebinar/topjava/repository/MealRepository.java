package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal meal, int userId);

    boolean deleteByUser(int userId, int id);

    Meal getByUser(int userId, int id);

    Collection<Meal> filter(LocalTime start, LocalTime end, int userId);

    Collection<Meal> filter(LocalDate start, LocalDate end, int userId);

    Collection<Meal> getAll();

    Collection<Meal> getAllByUser(int userId);
}
