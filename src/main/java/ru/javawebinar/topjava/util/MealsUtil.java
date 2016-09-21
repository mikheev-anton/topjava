package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    public static final List<Meal> MEALS = new ArrayList<>();

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<MealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY);
        filteredMealsWithExceeded.forEach(System.out::println);
//        System.out.println(getFilteredWithExceededByCycle(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }
    public static List<MealWithExceed> getWithExceededFilter(Collection<Meal> filterResult, Collection<MealWithExceed> allMeal) {
        Set<Integer> collect = filterResult.stream()
                .map(Meal::getId)
                .collect(Collectors.toSet());

        return allMeal.stream()
                .filter(mealWithExceed -> collect.contains(mealWithExceed.getId()))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);

    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal,caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static void initStartMeal(User user){
        MEALS.clear();
        MEALS.addAll(Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак пользователя: "+user.getId(), 500, user),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед пользователя: "+user.getId(), 1000, user),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин пользователя: "+user.getId(), 500, user),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак пользователя: "+user.getId(), 1000, user),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед пользователя: "+user.getId(), 500, user),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин пользователя: "+user.getId(), 510, user)
        ));
    }
}