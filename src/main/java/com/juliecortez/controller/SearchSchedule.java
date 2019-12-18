package com.juliecortez.controller;

import com.juliecortez.entity.Schedule;
import com.juliecortez.entity.ScheduleDetail;
import com.juliecortez.entity.User;
import com.juliecortez.persistence.GenericDao;
import com.juliecortez.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * A servlet used to search for schedules
 * @author JCortez
 */

@WebServlet(
    urlPatterns = {"/searchSchedule"}
)
public class SearchSchedule extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Declare variables and object referenes
        GenericDao scheduleDao = new GenericDao(Schedule.class);
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<Schedule> schedules = new ArrayList<Schedule>();
        String destination = "";

        // get parameters from request
        String searchType = req.getParameter("searchType");
        // If searching by Start Date or Adding a new schedule, need start date value entered by user
        if (!searchType.equals("findAllSchedules")) {
            startDate = LocalDate.parse(req.getParameter("startDate"));
        }

        // set request attributes and destination jsp value based on type of search (or add) requested by user
        if (searchType.equals("findAllSchedules")) {
            req.setAttribute("schedules", scheduleDao.getAll());
            destination = "scheduleSearchResults.jsp";
        } else {
            // Whether searching for a specific startDate or adding.  Need to check if schedule exists for the startDate
            schedules = getScheduleByStartDate(startDate);
            if (schedules.size() == 0) {
                // schedule not found, will send startDate to use for new schedule to be added
                req.setAttribute("userAction", "add");
                req.setAttribute("startDate", startDate);
                destination = "addEditScheduleServlet?userAction=add";
            } else {
                // schedule was already found, so will find that schedule for the user to edit
                req.setAttribute("userAction", "edit");
                destination = "addEditScheduleServlet?userAction=edit&id=" + schedules.get(0).getId();
            }
        }

        // if the currentWeather attribute does not exist for the session then call the weather api and set the attribute
        HttpSession session = req.getSession();
        if (session.getAttribute ("currentWeather") == null) {
            session.setAttribute("currentWeather", getCurrentWeather());
        }

        // forward the request based on action selected by the user
        RequestDispatcher dispatcher = req.getRequestDispatcher(destination);
        dispatcher.forward(req, resp);
    }

    /**
     * Gets a list of schedule objects that match by start date (should only be 1)
     *
     * @param startDate the start date
     * @return the schedule that qualified by by start date
     */
    public List<Schedule> getScheduleByStartDate(LocalDate startDate) {

        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        String hql = "select s FROM Schedule s WHERE s.startDate = :startDate ";
        Query<Schedule> query = session.createQuery(hql, Schedule.class);
        query.setParameter("startDate",startDate);
        List<Schedule> schedules = query.list();
        session.close();
        return schedules;
    }

    /**
     * This method will call the Check Weather Service class to call a weather api to get current weather info
     * @return current weather information
     */
    private String getCurrentWeather() {
        CheckWeatherService weatherService = new CheckWeatherService();
        String currentWeather = "";
        try {
            currentWeather = weatherService.callWeatherAPI();
        }
        catch(Exception e) {
            logger.error("Exception returned calling weather api" + e);
        }
        return currentWeather;
    }

}
