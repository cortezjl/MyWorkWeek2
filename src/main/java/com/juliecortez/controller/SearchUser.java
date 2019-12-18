package com.juliecortez.controller;

import com.juliecortez.entity.User;
import com.juliecortez.persistence.GenericDao;
import com.juliecortez.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * A servlet used to search for users based on the type of search the user chose.
 * @author JCortez
 */

@WebServlet(
        urlPatterns = {"/searchUser"}
)

public class SearchUser extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GenericDao userDao = new GenericDao(User.class);
        // get parameters from the request
        String searchType = req.getParameter("searchType");
        String searchField = req.getParameter("searchField");
        String searchValue = req.getParameter("searchValue");
        logger.info("searchType=" + searchType + " searchField=" + searchField + " searchValue=" + searchValue);
        // retrieve list of qualifying users based on the type of search requested
        if (searchType.equals("allUsers")) {
            // retrieve all users
            req.setAttribute("users", userDao.getAll());
        } else if (searchType.equals("activeUsers")) {
            // retrieve all users that are active as of today
            req.setAttribute("users", getUsersActiveToday());
        } else {
            // retrieve all users that qualify against the search field and value chosen by the user
            req.setAttribute("users", userDao.getByPropertyLike(searchField, searchValue));
        }


        // if the currentWeather attribute does not exist for the session then call the weather api and set the attribute
        HttpSession session = req.getSession();
        if (session.getAttribute ("currentWeather") == null) {
            session.setAttribute("currentWeather", getCurrentWeather());
        }

        // forward the request to the jsp that will display the qualifying user results
        RequestDispatcher dispatcher = req.getRequestDispatcher("userSearchResults.jsp");
        dispatcher.forward(req, resp);
    }


    /**
     * Gets a list of users that are active as of today.
     *
     * @return the users active today
     */
    private List<User> getUsersActiveToday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Session session = SessionFactoryProvider.getSessionFactory().openSession();

        String hql = "select u FROM User u WHERE u.startDate <= :endDate and u.endDate >= :startDate ";
        Query<User> query = session.createQuery(hql, User.class);
        LocalDate today = LocalDate.parse(LocalDate.now().format(formatter));
        query.setParameter("startDate",today);
        query.setParameter("endDate",today);
        List<User> users = query.list();

        session.close();
        return users;
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
