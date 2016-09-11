package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListRepositoryImp implements MealRepository<Meal, Integer> {

    private static ListRepositoryImp ourInstance = null;

    private final List<Meal> meals = new  CopyOnWriteArrayList();

    private Queue<Integer> queueOfFreeId = new ConcurrentLinkedQueue();

    private AtomicInteger counter = new AtomicInteger();

    public static ListRepositoryImp getInstance() {
        if (ourInstance==null){
            ourInstance = new ListRepositoryImp();
        }
        return ourInstance;
    }

    @Override
    public Meal read(Integer index) {
        return meals.get(index);
    }

    @Override
    public void update(Meal meal) {
        if (meals.contains(meal)){
            meals.set(meals.indexOf(meal),meal);
        }
        else{
            meal.setId(getKey());
            meals.add(meal);
        }
    }

    @Override
    public void delete(Integer index) {
        if (index>=0&&index<meals.size()){
            int freeId = meals.remove(index.intValue()).getId();
            this.queueOfFreeId.add(freeId);
        }
    }

    @Override
    public List<Meal> getAll() {
        return Collections.unmodifiableList(meals);
    }

    private Integer getKey() {

        if (queueOfFreeId.isEmpty())
            return counter.incrementAndGet();

        else
            return queueOfFreeId.poll();
    }

}
