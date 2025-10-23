package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.exceptions.DepartmentAlreadyExistsException;
import com.demo.model.DepartmentNameForm;
import com.demo.service.DepartmentService;


@Controller
public class AddNewDepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(
			path="/create-new-department", 
			method=RequestMethod.GET)
	public String showDepartmentNameForm (Model model) 
	{
		model.addAttribute("departmentData", new DepartmentNameForm());

		String message = "Enter Department Name";

		model.addAttribute("message", message);

		return "get-new-department-name";
	}


	@RequestMapping(
			path="/create-new-department", 
			method=RequestMethod.POST)
	public String CreateNewDepartment (
			Model model,
			@ModelAttribute("departmentData") DepartmentNameForm form)
	{
		String message = "Enter A New Department Name";
		String departmentName = "";
		String successMessage = "";
		String errorMessage = "";

		try {
			departmentName = form.getDepartmentName();
 		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			departmentService.createNewDepartment(departmentName);
			successMessage = "Department Created Successfully!";
		} catch (DepartmentAlreadyExistsException e) {
			// Department exists
			errorMessage = e.getMessage();
		} catch (IllegalArgumentException e) {
			// Department name is empty
			errorMessage = e.getMessage();
		} catch (RuntimeException e) {
			// Other errors
			errorMessage = e.getMessage();
		}

		model.addAttribute("message", message);
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("successMessage", successMessage);
		model.addAttribute("departmentData", new DepartmentNameForm());

		return "get-new-department-name";
	}
}
