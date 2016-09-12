package ru.javawebinar.topjava.repository;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface MealRepository<T, KEY extends Serializable> {
    T read(KEY key);
    void update(T t);
    void delete(KEY key);
    Collection<T> getAll();
    KEY getKey();
}
