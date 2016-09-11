package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.ListRepositoryImp;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MealListService implements MealService {

    private ListRepositoryImp repository = ListRepositoryImp.getInstance();

    public MealListService() {
        addAll(Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        ));
    }

    @Override
    public Meal readById(int id) {
        return getAll().stream()
                .filter(meal -> meal.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public Meal updateById(LocalDateTime dateTime, String description, int calories, int id) {
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(id);
        repository.update(meal);
        return meal;
    }


    @Override
    public Meal create(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(dateTime,description,calories);
        repository.update(meal);
        return meal;
    }

    @Override
    public void deleteById(int id) {
        Meal meal = readById(id);
        if (meal!=null)
            repository.delete(getAll().indexOf(meal));
    }

    @Override
    public List<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public void addAll(Collection<Meal> collection) {
        collection.stream().peek(meal -> create(meal.getDateTime(),meal.getDescription(),meal.getCalories())).count();
    }
}
