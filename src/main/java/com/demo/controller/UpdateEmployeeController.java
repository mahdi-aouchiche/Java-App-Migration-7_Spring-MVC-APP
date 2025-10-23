package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.service.EmployeeService;

@Controller
public class UpdateEmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(path= "/update-employee", method = {RequestMethod.POST})
	public String updateEmployee(
			@RequestParam("id") int employeeId,
			@RequestParam("name") String name,
			@RequestParam("age") int age,
			@RequestParam("salary") double salary,
			@RequestParam("returnURL") String returnURL,
			RedirectAttributes redirectAttributes)
	{
		if(name == null || name.trim().isEmpty() || salary < 0 || age < 18 || age > 100) {
			redirectAttributes.addFlashAttribute(
					"error", 
					"Invalid employee data provided.");
			return "redirect:" + returnURL;
		}

		int result = employeeService.updateEmployee(employeeId, name, age, salary);

		if(result == 1) { // Employee Updated
			redirectAttributes.addFlashAttribute(
					"message", 
					"Employee ID " + employeeId + " updated successfully.");
		} else {
			redirectAttributes.addFlashAttribute(
					"error",
					"Update failed. Employee exists already!");
		}
		return "redirect:" + returnURL;
	}
}
