package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;

@Controller
public class ViewNonAffiliatedEmployeesController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(
			path = "/view-non-affiliated-employees",
			method = {RequestMethod.GET, RequestMethod.POST})
	public String viewNonAffiliatedEmployees(
			Model model, 
			HttpServletRequest request)
	{
		/* Get all employees */
		String message = "Non-Affiliated Employees Information";
		List<Employee> employeesNotAssociatedToDepartment = new ArrayList<>();
		List<String> columnLabels = new ArrayList<>();
		this.employeeService.listOfEmployeesNotAssociatedToDepartment(
				columnLabels, 
				employeesNotAssociatedToDepartment);

		// Get the path that was successfully mapped by the handler
	    String returnURL = (String) request.getAttribute(
	        HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE
	    );

		model.addAttribute("tableHeader", columnLabels);
		model.addAttribute("employeeList", employeesNotAssociatedToDepartment);
		model.addAttribute("returnURL", returnURL);
		model.addAttribute("informationType", message);

		return "view-edit-employees";
	}
}
