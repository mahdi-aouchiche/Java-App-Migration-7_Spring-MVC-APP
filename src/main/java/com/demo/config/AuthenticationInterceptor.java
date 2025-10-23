package com.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		// Retreive the session without creating a new one
		HttpSession session = request.getSession(false);

		// Check for null session or missing 'username' attribute
        if (session == null || session.getAttribute("username") == null) {

            // Redirect to the login page, adding an error parameter
            String loginUrl = request.getContextPath() + "/userLogin?error=unauthorized";
            response.sendRedirect(loginUrl);

            // Stop the request from proceeding to the controller
            return false;
        }

        // Session is valid, allow the request to proceed
		return true;
	}
}
