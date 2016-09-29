package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(mealService.getAll(NIKITA_ID), getNikitaMeal());
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        List<Meal> collectDB = mealService.getAll(NIKITA_ID).stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(),
                        LocalDateTime.MIN, LocalDateTime.parse("2016-09-24T16:00")))
                .collect(Collectors.toList());
        List<Meal> collectTest = getNikitaMeal().stream().filter(um -> TimeUtil.isBetween(um.getDateTime(),
                LocalDateTime.MIN, LocalDateTime.parse("2016-09-24T16:00")))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(collectDB, collectTest);
    }
    @Test
    public void testGet() throws Exception {
        Meal meal = mealService.get(FIRST_MEAL_USER.getId(), USER_ID);
        MATCHER.assertEquals(FIRST_MEAL_USER, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetForeign() throws Exception {
        mealService.get(FIRST_MEAL_USER.getId(), ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        mealService.delete(FIRST_MEAL_ADMIN.getId(), ADMIN_ID);
        List<Meal> adminMeal = getAdminMeal();
        adminMeal.remove(0);
        MATCHER.assertCollectionEquals(mealService.getAll(ADMIN_ID), adminMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteForeign() throws Exception {
        mealService.delete(FIRST_MEAL_ADMIN.getId(), NIKITA_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = SECOND_MEAL_NIKITA;
        Meal upMeal = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        upMeal.setCalories(20);
        upMeal.setDescription("UpdateMeal");
        mealService.save(upMeal, NIKITA_ID);
        MATCHER.assertEquals(upMeal,mealService.get(upMeal.getId(),NIKITA_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateForeign() throws Exception {
        Meal meal = mealService.get(FIRST_MEAL_USER.getId(), USER_ID);
        meal.setDescription("Barada");
        mealService.save(meal, ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        Meal meal = mealService.save(new Meal(LocalDateTime.now(), "New meal", 300), NIKITA_ID);
        List<Meal> nikitaMeal = new ArrayList<>(getNikitaMeal());
        nikitaMeal.add(meal);
        MATCHER.assertCollectionEquals(mealService.getAll(NIKITA_ID), nikitaMeal);
    }
}