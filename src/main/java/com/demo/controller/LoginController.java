package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.LoginForm;

@Controller
public class LoginController {

	@RequestMapping(
			path = "/userLogin", 
			method = RequestMethod.GET)
	public String loadLoginForm(
			@RequestParam(value = "error", required = false) String error ,
			Model model)
	{
		LoginForm loginForm = new LoginForm();

		// Check if the request was redirected here due to an unauthorized attempt
        if ("unauthorized".equals(error)) {
            String needToLogin = "<p style='color:blue; font-weight:bold;'>"
                    + "Please enter your credentials to access menu.</p>";

            model.addAttribute("message", needToLogin);
        }

        model.addAttribute("loginForm", loginForm);

		// return the view page login.jsp
		return "login";
	}

	@RequestMapping(
			path="/userLogin", 
			method=RequestMethod.POST)
	public ModelAndView processLoginFormInput(
			LoginForm loginForm, 
			BindingResult result, 
			HttpSession session)
	{
		ModelAndView modelAndView = null;

		//TODO: Use a service layer for real authentication logic, not hardcoded checks
		if("Admin".equals(loginForm.getUsername())
				&& "123".equals(loginForm.getPassword()))
		{
			session.setAttribute("username", loginForm.getUsername());

			// Go to the option menu controller
			modelAndView = new ModelAndView("redirect:/optionMenu");
		} else {
			// Stay in login view and display invalid login message
			modelAndView = new ModelAndView("login");

			String invalidLogin = "<p style='color:red; font-weight:bold;'> "
								+ "Invalid Username or Password </p>";

			modelAndView.addObject("message", invalidLogin);
		}

		return modelAndView;
	}
}