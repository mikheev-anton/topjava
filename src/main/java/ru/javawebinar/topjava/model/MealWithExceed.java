package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 11.01.2015.
 */
public class MealWithExceed {

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    private final int id;

    public MealWithExceed(LocalDateTime dateTime, String description, int calories,int id, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.id=id;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public int getId() {
        return id;
    }
}