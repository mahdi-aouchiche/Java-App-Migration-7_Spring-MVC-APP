package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.service.EmployeeService;


@Controller
public class ViewSecondHighestSalaryController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(
			path = "/view-second-highest-salary", 
			method = RequestMethod.GET)
	public String viewSecondhighestSalary(Model model) 
	{
		String message = "Company's Second Highest Salary";
		double secondHighestSalary = this.employeeService
										 .secondMaxEmployeesSalary();

		model.addAttribute("informationType", message);
        model.addAttribute("secondHighestSalary", secondHighestSalary);

        return "view-second-highest-salary";
	}
}
