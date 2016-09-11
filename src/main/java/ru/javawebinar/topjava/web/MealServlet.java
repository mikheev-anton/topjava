package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealListService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private MealListService service = new MealListService();

    private static final Logger LOG = getLogger(MealServlet.class);

    private int idForEdit;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action!=null)
            switch (action){
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    service.deleteById(id);
                    resp.sendRedirect("meal");
                    return;
                case "edit":
                    idForEdit = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("mealForEdit", service.readById(idForEdit));
                    req.getRequestDispatcher("editMeal.jsp").forward(req,resp);
                    break;
            }

        List<MealWithExceed> allExceedMeal = MealsUtil.getFilteredWithExceeded(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealList", allExceedMeal);
        req.getRequestDispatcher("mealList.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType ("text/html; charset=UTF-8");
        req.setCharacterEncoding ("UTF-8");

        int calories = Integer.parseInt(req.getParameter("cal"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("date"));

        String action = req.getParameter("action");

        if (action.equals("edit"))
            service.updateById(dateTime, req.getParameter("desc"),calories, idForEdit);
        else if (action.equals("add"))
            service.create(dateTime, req.getParameter("desc"),calories);
        resp.sendRedirect("meal");
    }
}
