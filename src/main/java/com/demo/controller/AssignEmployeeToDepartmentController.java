package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entity.Department;
import com.demo.entity.Employee;
import com.demo.service.DepartmentService;
import com.demo.service.EmployeeService;

@Controller
public class AssignEmployeeToDepartmentController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	
	@RequestMapping(
			path= "/assign-employee-to-department", 
			method = RequestMethod.GET)
	public String displayEmployeeAndDepartments(Model model) 
	{
		// Get the lists of employees and departments
		List<Employee> employees = this.employeeService.getEmployeeList();
		List<Department> departments = this.departmentService.getDepartmentList();
		
		model.addAttribute("employees", employees);
		model.addAttribute("departments", departments);
		
		// send the lists to the views .jsp
		return "assign-employee-to-department";
	}
	
	
	@RequestMapping(
			path= "/assign-employee-to-department", 
			method= RequestMethod.POST)
	public String assignEmployeeToDepartment(
			Model model,
			HttpSession session,
			@RequestParam("employeeId") int employeeId,
			@RequestParam("departmentId") int departmentId) 
	{
		// Add the employee to the department
		int result = this.employeeService
						 .addEmployeeToDepartment(departmentId, employeeId);
		
		String successMessage = "";
		String errorMessage = "";
		
		if(result == -1) {
			errorMessage = "Employee or Department Not Found";
		} else if(result == 0) {
			errorMessage = "Employee is Added to the Department Already";
		} else if(result == 1) {
			successMessage = "Employee Added to the Department Successfully";
		} else {
			errorMessage = "An Error Occured!";
		}
		
		// Fetch the updated lists
		List<Employee> employees = this.employeeService.getEmployeeList();
		List<Department> departments = this.departmentService.getDepartmentList();
		
		// Pass updated data and message to the views .jsp
		model.addAttribute("employees", employees);
		model.addAttribute("departments", departments);
		model.addAttribute("successMessage", successMessage);
		model.addAttribute("errorMessage", errorMessage);
		
		return "assign-employee-to-department";
	}
}
