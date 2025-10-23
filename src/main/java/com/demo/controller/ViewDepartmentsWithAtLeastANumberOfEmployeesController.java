package com.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.entity.Department;
import com.demo.model.NumberEmployeesForm;
import com.demo.service.DepartmentService;


@Controller
public class ViewDepartmentsWithAtLeastANumberOfEmployeesController
{
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(
			path = "/view-departments-with-at-least-a-number-of-employees",
			method=RequestMethod.GET)
	public String showNumberEmployeesForm(Model model) 
	{
		model.addAttribute("numberEmployees", new NumberEmployeesForm());

		String message = "Enter The Least Number Of Employees Per Department";
        model.addAttribute("message", message);

        return "get-number-of-employees";
	}

	@RequestMapping(
			path = "/view-departments-with-at-least-a-number-of-employees",
			method=RequestMethod.POST)
	public String viewDepartmentsWithAtLeastANumberOfEmployees (
			Model model,
			@ModelAttribute("numberEmployees") NumberEmployeesForm form)
	{
		int numEmployees = 0;
		String message = "";

		// Validate Input
		String numEmployeesStr = form.getNumEmployees();

        try {
            numEmployees = Integer.parseInt(numEmployeesStr);

            if (numEmployees < 0) {
                throw new NumberFormatException("Enter a Number Greater or Equal to 0");
            }
        } catch (NumberFormatException e) {
            // If validation fails, return to the form page with an error message
            message = "<p style='color:orange; font-weight:bold; text-align:center;'>" +
                         "Please Enter A Valid Minimum Number Of Employees" +
                      "</p>";

            model.addAttribute("message", message);

            return "get-number-of-employees";
        }

		// Display message in the view .jsp
		if(numEmployees == 0) {
			message = "Employee Count For Each Department";
		} else if(numEmployees == 1) {
			message = "Departments With At Least " + numEmployees + " Employee";
		} else {
			message = "Departments With At Least " + numEmployees + " Employees";
		}

		/* Send info to service layer */
		List<String> columnLabel = new ArrayList<>();
		LinkedHashMap<Department, Integer> records = new LinkedHashMap<>();

		this.departmentService
			.departmentsWithAtLeastACertainNumberOfEmployees(
				numEmployees, columnLabel, records);

		/* Dispatch to view-jsp*/
		model.addAttribute("informationType", message);
		model.addAttribute("departmentList", records);
		model.addAttribute("columnLabel", columnLabel);

		return "view-departments-by-number-of-employees";
	}
}
