package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private AdminRestController adminUserController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            adminUserController = appCtx.getBean(AdminRestController.class);
            mealRestController = appCtx.getBean(MealRestController.class);
            adminUserController.create(new User(0, "Админ", "email", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(1, "Первый", "email", "password", Role.ROLE_USER));
            adminUserController.create(new User(2, "Второй", "email", "password", Role.ROLE_USER));
        initStartMeal(1);
        initStartMeal(2);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
        LOG.info("Close context and servlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("userN", AuthorizedUser.id());
            request.setAttribute("mealList",mealRestController.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ? mealRestController.save(
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000,adminUserController.get(AuthorizedUser.id()))) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        if (userId!=null){
            AuthorizedUser.setId(Integer.parseInt(userId));
            if (userId.equals("0")){
                UserServlet.setUsers(adminUserController.getAll());
                response.sendRedirect("users");
            }
            else response.sendRedirect("meals");
            return;
        }
        if (action==null){
            String id = request.getParameter("id");

            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")),adminUserController.get(AuthorizedUser.id()));
            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.save(meal);
            response.sendRedirect("meals");
        }
        else if (action.equals("filter")){
            LOG.info("Filter");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (!startDate.isEmpty()||!endDate.isEmpty()||!startTime.isEmpty()||!endTime.isEmpty()){
                List<MealWithExceed> meals = mealRestController.filter(startDate, endDate, startTime, endTime);
                request.setAttribute("mealList", meals);
                request.setAttribute("dateS", startDate);
                request.setAttribute("dateE", endDate);
                request.setAttribute("timeS", startTime);
                request.setAttribute("timeE", endTime);
                request.getRequestDispatcher("mealList.jsp").forward(request, response);
            }
            else
                response.sendRedirect("meals");
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private void initStartMeal(int userId){
        User user = adminUserController.get(userId);
        MealsUtil.initStartMeal(user);
        MealsUtil.MEALS.stream()
                .peek(meal1 -> mealRestController.initSave(meal1, userId)).count();
    }
}
