package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.service.EmployeeService;

@Controller
public class ViewMinAndMaxSalariesController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(
			path = "/view-minimum-and-maximum-salaries",
			method = RequestMethod.GET)
	public String viewMinAndMaxSalaries (Model model) 
	{
		String message = "Company's Lowest and Highest Salaries";
		List<Double> minAndMAxSalary = this.employeeService
										   .getMinAndMaxSalary();

		model.addAttribute("informationType", message);
		model.addAttribute("minSalary", minAndMAxSalary.get(0));
		model.addAttribute("maxSalary", minAndMAxSalary.get(1));

		return "view-minimum-and-maximum-salaries";
	}
}
