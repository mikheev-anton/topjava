package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NIKITA_ID = START_SEQ + 2;

    public static final Meal FIRST_MEAL_USER = new Meal(1, LocalDateTime.parse("2016-09-24T11:00:00"), "Завтрак", 700);
    public static final Meal SECOND_MEAL_USER = new Meal(2,LocalDateTime.parse("2016-09-24T13:00:00"), "Обед", 1000);
    public static final Meal THIRD_MEAL_USER = new Meal(3,LocalDateTime.parse("2016-09-24T16:00:00"), "Ужин", 1000);
    public static final Meal FIRST_MEAL_ADMIN = new Meal(4,LocalDateTime.parse("2016-09-24T10:00:00"), "Полдник", 500);
    public static final Meal SECOND_MEAL_ADMIN = new Meal(5,LocalDateTime.parse("2016-09-24T12:00:00"), "Дожер", 700);
    public static final Meal THIRD_MEAL_ADMIN = new Meal(6,LocalDateTime.parse("2016-09-24T15:00:00"), "Чипсы", 500);
    public static final Meal FIRST_MEAL_NIKITA = new Meal(7,LocalDateTime.parse("2016-09-24T11:00:00"), "Хороший борщ с капусткой, но не красный", 700);
    public static final Meal SECOND_MEAL_NIKITA = new Meal(8,LocalDateTime.parse("2016-09-24T13:00:00"), "Сосисачки", 700);
    public static final Meal THIRD_MEAL_NIKITA = new Meal(9,LocalDateTime.parse("2016-09-24T16:00:00"), "Какой-то непонятный салатик", 300);
    public static final Meal FOURTH_MEAL_NIKITA = new Meal(10,LocalDateTime.parse("2016-09-24T20:00:00"), "Чай, утоляющий жажду", 50);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>((expected, actual) -> expected == actual ||
            (Objects.equals(expected.getCalories(), actual.getCalories())
                    && Objects.equals(expected.getId(), actual.getId())
                    && Objects.equals(expected.getDateTime(), actual.getDateTime())
                    && Objects.equals(expected.getDescription(), actual.getDescription())
            ));

    public static List<Meal> getUserMeal(){
        return new ArrayList<>(Arrays.asList(
                FIRST_MEAL_USER,
                SECOND_MEAL_USER,
                THIRD_MEAL_USER));
    }

    public static List<Meal> getAdminMeal(){
        return new ArrayList<>(Arrays.asList(
                FIRST_MEAL_ADMIN,
                SECOND_MEAL_ADMIN,
                THIRD_MEAL_ADMIN));
    }

    public static List<Meal> getNikitaMeal(){
        return new ArrayList<>(Arrays.asList(
                FIRST_MEAL_NIKITA,
                SECOND_MEAL_NIKITA,
                THIRD_MEAL_NIKITA,
                FOURTH_MEAL_NIKITA
        ));
    }
}
