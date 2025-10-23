package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;

@Controller
public class ViewEmployeesEarningSecondHighestSalaryController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(
			path = "/view-employees-earning-second-highest-salary", 
			method = RequestMethod.GET)
	public String viewEmployeesEarningSecondHighestSalary(Model model) 
	{
		// Type of displayed information
		String message = "Employees Earning Second Highest Salary Information";

		// Get info from service layer
		List<Employee> employeesEarningSecondMaximumSalary = new ArrayList<>();
		List<String> columnLabels = new ArrayList<>();
		this.employeeService.employeesEarningSecondMaximumSalary(
				columnLabels,
				employeesEarningSecondMaximumSalary
		);

		// Send data to JSP
		model.addAttribute("informationType", message);
		model.addAttribute("columnLabel", columnLabels);
		model.addAttribute("records", employeesEarningSecondMaximumSalary);

		// return views .jsp file
		return "view-employees-earning-second-highest-salary";
	}
}
