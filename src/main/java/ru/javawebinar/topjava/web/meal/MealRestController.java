package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Collections;
import java.util.List;


@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal get(int id){
        return service.get(id, AuthorizedUser.id());
    }
    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public Meal save(Meal meal) {
       return service.save(meal, AuthorizedUser.id());
    }

    //Только для инициализации
    public Meal initSave(Meal meal, int userId){
        return service.save(meal, userId);
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id());
    }

    public List<MealWithExceed> filter(String... dateTime) {
        return service.filter(AuthorizedUser.id(), dateTime);
    }
}
