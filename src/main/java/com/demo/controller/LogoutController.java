package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if(session != null) {
			session.invalidate();
		}

		// redirect to home page "index.jsp"
		return "redirect:/";
	}
}

