package com.coveros.training;

import com.coveros.training.domainobjects.RegistrationResult;
import com.coveros.training.persistence.RegistrationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"}, loadOnStartup = 1)
public class RegisterServlet extends HttpServlet {

    private static final String PASSWORD_PARAM = "password";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RegisterServlet.class);
    private static final String USERNAME_PARAM = "username";
    static RegistrationUtils registrationUtils = new RegistrationUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(USERNAME_PARAM);
        request.setAttribute(USERNAME_PARAM, username);

        String password = request.getParameter(PASSWORD_PARAM);
        request.setAttribute(PASSWORD_PARAM, password);

        String responseText;

        if (username.isEmpty()) {
            responseText = "no username provided";
        } else if (password.isEmpty()) {
            responseText = "no password provided";
        } else {

            logger.info("received request to register a user, {}", username);

            RegistrationResult registrationResult = registrationUtils.processRegistration(username, password);

            responseText = registrationResult.toPrettyString();
        }

        request.setAttribute("result", responseText);
        request.setAttribute("return_page", "library.html");
        forwardToResult(request, response, logger);
    }

    /**
     * Wrapping a static method call for testing.
     */
    private void forwardToResult(HttpServletRequest request, HttpServletResponse response, Logger logger) {
        ServletUtils.forwardToResult(request, response, logger);
    }

}

