package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealService {
    Meal readById(int id);
    Meal updateById(LocalDateTime dateTime, String description, int calories, int id);
    Meal create(LocalDateTime dateTime, String description, int calories);
    void deleteById(int id);
    Collection<Meal> getAll();
    void addAll(Collection<Meal> collection);
}
