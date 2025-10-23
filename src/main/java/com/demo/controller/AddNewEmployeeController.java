package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.exceptions.EmployeeAlreadyExistsException;
import com.demo.model.EmployeeDataForm;
import com.demo.service.EmployeeService;


@Controller
public class AddNewEmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(
			path="/add-new-employee", 
			method = RequestMethod.GET)
	public String showEmployeeDataForm(Model model) 
	{
		model.addAttribute("employeeData", new EmployeeDataForm());

		String message = "Enter New Employee Details";
		model.addAttribute("message", message);

		return "get-new-employee-information";
	}


	@RequestMapping(
			path="add-new-employee", 
			method = RequestMethod.POST)
	public String addNewEmployee(
			Model model,
			@ModelAttribute("employeeData") EmployeeDataForm form)
	{
		String firstname = "";
		String lastname = "";
		String fullname = "";
		String message = "Enter New Employee Details";
		String successMessage = "";
		String errorMessage = "";
		int age = 0;
		double salary = 0;


		try {
			firstname = form.getFirstname();
			lastname = form.getLastname();
			age = form.getAge();
			salary = form.getSalary();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Add new employee using service layer */
		if(firstname.length() < 1) {
			message = "First Name Cannot Be Empty!";
		} else if (lastname.length() < 1) {
			message = "Last Name Cannot Be Empty!";
		} else if (age < 18 || age > 100) {
			message = "Age Must Be Between 18 to 100";
		} else if (salary < 0) {
			message = "Salary Cannot Be Less Than Zero!";
		} else {
			fullname = firstname + " " + lastname;

			try {
				employeeService.addNewEmployee(firstname, lastname, age, salary);
				// Employee Added Successfully
				successMessage = "Employee \"" + fullname + "\" Added Successfully!";

			} catch (EmployeeAlreadyExistsException e) {
				errorMessage = e.getMessage();
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		}

		// Re-display the form page
		model.addAttribute("message", message);
		model.addAttribute("successMessage", successMessage);
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("employeeData", new EmployeeDataForm());
		return "get-new-employee-information";
	}
}
