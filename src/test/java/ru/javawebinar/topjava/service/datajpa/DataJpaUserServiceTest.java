package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void testGetWithMeal() throws Exception{
        User user = service.getWithMeal(ADMIN_ID);
        User adminWithMeal = ADMIN_WITH_MEAL;
        user.getUserMeal().get(0);
        adminWithMeal.getUserMeal().get(0);
        MATCHER.assertEquals(user, adminWithMeal);
    }

    @Test(expected = LazyInitializationException.class)
    public void testGetWithLazyMeal() throws Exception{
        User user = service.get(ADMIN_ID);
        List<Meal> userMeal = user.getUserMeal();
        userMeal.get(0);
    }
}
