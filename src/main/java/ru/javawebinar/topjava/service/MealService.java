package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.to.MealWithExceed;

import java.util.Collection;
import java.util.List;


public interface MealService {
    Meal get(int id, int userId);
    void delete(int id, int userId);
    Meal save(Meal meal, int usetId);
    List<MealWithExceed> filter(int userId, String... dateTime);
    List<MealWithExceed> getAll(int userId);
}
