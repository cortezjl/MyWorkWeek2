package com.juliecortez.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliecortez.entity.TimeOffRequest;
import com.juliecortez.entity.User;
import com.juliecortez.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openweathermap.WeatherResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


/**
 *  This servlet is user to Add or Edit a time off request
 *
 * @author JCortez
 */
@WebServlet(
        urlPatterns = {"/addEditTimeOffRequestServlet"}
)

public class AddEditTimeOffRequestServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionToPerform = request.getParameter("actionToPerform");
        int idToProcess = 0;
        String message = "";
        GenericDao timeOffRequestDao = new GenericDao(TimeOffRequest.class);

        if (actionToPerform.equals("add")) {
            // Add time off request for the user, first make sure username is a valid user
            List<User> userList = getUserByUserName(request.getParameter("userName"));
            if (userList.size() == 0) {
                // user name is not found, so send request info back for user to correct
                User user = null;
                TimeOffRequest timeOffRequest = new TimeOffRequest(request.getParameter("userName"),
                        LocalDateTime.parse((request.getParameter("startDate"))),
                        LocalDateTime.parse((request.getParameter("endDate"))),
                        user);
                message = "Username is not a valid user";
                request.setAttribute("timeOffRequest",(TimeOffRequest)timeOffRequest);
                request.setAttribute("timeOffRequestAction", "add");
            } else {
                // user name is valid, can add the time off request
                TimeOffRequest timeOffRequest = new TimeOffRequest(request.getParameter("userName"),
                        LocalDateTime.parse((request.getParameter("startDate"))),
                        LocalDateTime.parse((request.getParameter("endDate"))),
                        userList.get(0));
                idToProcess = timeOffRequestDao.insert(timeOffRequest);
                message = "Time off request has been added";
                TimeOffRequest timeOffRequestAdded = (TimeOffRequest) timeOffRequestDao.getById(idToProcess);
                request.setAttribute("timeOffRequestAction", "edit");
                request.setAttribute("timeOffRequest", timeOffRequestAdded);
            }
        } else if (actionToPerform.equals("edit")) {
            // Set id of time off request to edit
            idToProcess = Integer.valueOf(request.getParameter("id"));
            logger.info("post will update time off request for id#" + idToProcess);
            // Create time off request object and populate by retrieving TimeOffRequest object values by id
            TimeOffRequest timeOffRequest = (TimeOffRequest)timeOffRequestDao.getById(idToProcess);
            // Update fields from values changed on the screen
            timeOffRequest = setTimeOffRequestValuesFromForm(request, timeOffRequest);
            timeOffRequestDao.saveOrUpdate(timeOffRequest);
            request.setAttribute("timeOffRequestAction", "edit");
            message = "Time off request has been updated";
            request.setAttribute("timeOffRequest",(TimeOffRequest)timeOffRequestDao.getById(idToProcess));
        }
        // Add a message from adding or updating the User
        request.setAttribute("timeOffRequestUpdateMessage", message);
        // forward the request to the page to add or edit a time off request
        RequestDispatcher dispatcher = request.getRequestDispatcher("/timeOffRequestAddEdit.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GenericDao timeOffRequestDao = new GenericDao(TimeOffRequest.class);
        String actionToPerform = request.getParameter("timeOffRequestAction");
        // set request attributes based on the action to perform (add or edit)
        if (actionToPerform.equals("edit")) {
            request.setAttribute("timeOffRequest",timeOffRequestDao.getById(Integer.valueOf(request.getParameter("id"))));
            request.setAttribute("timeOffRequestAction", "edit");
        } else if (actionToPerform.equals("add")) {
            request.removeAttribute("timeOffRequest");
            request.setAttribute("timeOffRequestAction", "add");
        }

        // forward the request to the page to add or edit a time off request
        RequestDispatcher dispatcher = request.getRequestDispatcher("/timeOffRequestAddEdit.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * set the starting and ending date/time for the time off request with the values the user entered on the form
     * @param request  the http request
     * @param timeOffRequest the time off request object to be updated
     * @return the updated time off request object
     */
    private TimeOffRequest setTimeOffRequestValuesFromForm(HttpServletRequest request, TimeOffRequest timeOffRequest) {
        timeOffRequest.setStartDate(LocalDateTime.parse(request.getParameter("startDate")));
        timeOffRequest.setEndDate(LocalDateTime.parse((request.getParameter("endDate"))));
        return timeOffRequest;
    }

    /**
     * Retrieve a user by user name
     * @param userName user name value of the user
     * @return User object that matched by user name
     */
    private List<User> getUserByUserName(String userName) {
        GenericDao userDao =  new GenericDao(User.class);
        List<User> users = userDao.getByPropertyEqual("userName", userName);
        return users;
    }
}
