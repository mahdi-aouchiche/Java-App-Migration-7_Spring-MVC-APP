package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.service.EmployeeService;

@Controller
public class ViewAverageSalaryController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(
			path = "/view-average-salary", 
			method = RequestMethod.GET)
	public String viewAverageSalary(Model model) 
	{

		String message = "Company's Average Salary";
		double averageSalary = this.employeeService
								   .getEmployeesAverageSalary();

		model.addAttribute("informationType", message);
		model.addAttribute("averageSalary", averageSalary);

		return "view-average-salary";
	}
}
