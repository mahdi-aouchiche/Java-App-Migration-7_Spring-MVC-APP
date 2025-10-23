package com.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.entity.Department;
import com.demo.service.DepartmentService;

@Controller
public class ViewAverageSalaryByDepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(
			path = "/view-average-salary-by-department", 
			method = RequestMethod.GET)
	public String viewAverageSalaryByDepartment (Model model) 
	{
		String message = "Departments' Average Salary";

		// Table Column Names
		List<String> columnLabel = new ArrayList<>();

		// Get the data
		LinkedHashMap<Department, Double> records = this.departmentService
								.getAverageSalaryByDepartment(columnLabel);

		// Send data to JSP
		model.addAttribute("informationType", message);
		model.addAttribute("columnLabel", columnLabel );
		model.addAttribute("records", records );

		return "view-average-salary-by-department";
	}
}
