package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;

@Controller
public class DeleteAnEmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(
			path = "/delete-an-employee", 
			method = RequestMethod.GET)
	public String getEmployeeToDelete(
			Model model,
			@RequestParam(name = "returnURL", required = false) String returnURL,
			@RequestParam(name = "employeeId", required = false) Integer employeeId,
			RedirectAttributes redirectAttributes)
	{	
		if(returnURL != null) { // request from view-edit-information.jsp
			if(this.employeeService.deleteEmployee(employeeId)) {
				redirectAttributes.addFlashAttribute(
	 					"message",
	 					"Employee ID = " + employeeId + ". Delete Successful!" );
	 			 
			} else {
				redirectAttributes.addFlashAttribute(
						"error", 
						"Employee ID = " + employeeId + ". Delete Unsuccessful!" );
			}
			
			return "redirect:" + returnURL;
			
		} else {	// request from option-menu.jsp
			
			List<Employee> employees = this.employeeService.getEmployeeList();
			model.addAttribute("employees", employees);
			
			return "delete-an-employee";
		}	
	}
	
	
	@RequestMapping(
			path = "/delete-an-employee", 
			method = RequestMethod.POST)
	public String deleteAnEmployee(
			Model model,
			@RequestParam("employeeId") int employeeId) 
	{
		if(this.employeeService.deleteEmployee(employeeId)) {
			model.addAttribute(
					"successMessage", 
					"Employee ID = " + employeeId + ". Delete Successful!");
		} else {
			model.addAttribute(
					"errorMessage", 
					"Employee ID = " + employeeId + ". Delete Unsuccessful!");
		}
		
		List<Employee> employees = this.employeeService.getEmployeeList();
		model.addAttribute("employees", employees);
		
		return "delete-an-employee";
	}
}
