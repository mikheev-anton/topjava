package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealServiceTest;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void testGetAllWithUser() throws Exception {
        List<Meal> allWithUser =(List<Meal>)service.getAllWithUser(USER_ID);
        checkLazyInitialization(allWithUser);
        MATCHER.assertCollectionEquals(MEALS, allWithUser);
    }

    @Test(expected = LazyInitializationException.class)
    public void testGetAllWithLazyUser() throws Exception{
        checkLazyInitialization((List<Meal>) service.getAll(ADMIN_ID));
    }

    private void checkLazyInitialization(List<Meal> serviceData) throws LazyInitializationException{
        Objects.requireNonNull(serviceData);
        serviceData.get(0).getUser().getEmail();
    }
}
