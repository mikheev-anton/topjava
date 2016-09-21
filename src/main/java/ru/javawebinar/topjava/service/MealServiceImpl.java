package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal get(int id, int userId) {
      return ExceptionUtil.checkNotFoundWithId(repository.getByUser(userId, id), id);
    }

    @Override
    public void delete(int id, int userId) {
        ExceptionUtil.checkNotFoundWithId(repository.deleteByUser(userId, id), id);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Meal save = repository.save(meal, userId);
        return ExceptionUtil.checkNotFoundWithId(save, save.getId());
    }

    @Override
    public List<MealWithExceed> filter(int userId, String... dateTime) {

        Map<Integer,LocalDate> dates = new HashMap<>();
        Map<Integer,LocalTime> times = new HashMap<>();

        int dataCount = 0;
        int timeCount = 0;
        for (int i = 0; i < dateTime.length; i++) {
            if (dateTime[i].contains(":")){
                times.put(++timeCount,LocalTime.parse(dateTime[i]));
                continue;
            }
            else if (dateTime[i].contains("-")){
                dates.put(++dataCount,LocalDate.parse(dateTime[i]));
                continue;
            }
            if (i<2)
                dataCount++;
            if (i>=2)
                timeCount++;
        }

        List<Meal> result = getSortListFromRepository(dates, times, userId);

        if (!times.isEmpty()&&!dates.isEmpty()){
            if (times.size()==2)
                result =  result.stream()
                        .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(),times.get(1), times.get(2)))
                        .collect(Collectors.toList());
            else if (times.size()==1 && times.get(1)!=null)
                result =  result.stream()
                        .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(),times.get(1), LocalTime.MAX))
                        .collect(Collectors.toList());
            else if (times.size()==1 && times.get(2)!=null)
                result =  result.stream()
                        .filter(meal -> TimeUtil.isBetweenTime(meal.getTime(), LocalTime.MIN,times.get(2)))
                        .collect(Collectors.toList());
        }

        return result.isEmpty() ? Collections.emptyList() : MealsUtil.getWithExceededFilter(result, getAll(userId));
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        List<Meal> meals = (List<Meal>) repository.getAllByUser(userId);
        return MealsUtil.getWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    private List<Meal> getSortListFromRepository(Map<Integer, LocalDate> dates, Map<Integer, LocalTime> times , int userId) {
        List<Meal> result = new ArrayList<>();

        if (!dates.isEmpty()&&dates.size()==2)
            result = (List<Meal>) repository.filter(dates.get(1),dates.get(2), userId);

        else if (!dates.isEmpty()&&dates.size()==1&&dates.get(1)!=null)
            result = (List<Meal>) repository.filter(dates.get(1), LocalDate.MAX, userId);

        else if (!dates.isEmpty()&&dates.size()==1&&dates.get(2)!=null)
            result = (List<Meal>) repository.filter(LocalDate.MIN, dates.get(2), userId);

        else if (dates.isEmpty()&&!times.isEmpty()){
            if (times.size()==2)
                result =  (List<Meal>) repository.filter(times.get(0), times.get(1), userId);
            else if (times.size()==1&& times.get(1)!=null)
                result =  (List<Meal>) repository.filter(times.get(1), LocalTime.MAX, userId);
            else if (times.size()==1 && times.get(2)!=null)
                result =  (List<Meal>) repository.filter(LocalTime.MIN, times.get(2), userId);
        }

        return result;
    }
}
