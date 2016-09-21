package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity implements Comparable<Meal> {

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final User owner;

    public Meal(LocalDateTime dateTime, String description, int calories, User owner) {
        this(null, dateTime, description, calories, owner);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, User owner) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.owner = owner;
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public User getOwner() {
        return owner;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    @Override
    public int compareTo(Meal o) {
        return this.getDateTime().compareTo(o.getDateTime());
    }
}
