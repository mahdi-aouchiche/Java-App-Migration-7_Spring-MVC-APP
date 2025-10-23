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
public class ViewEmployeeCountByDepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(
			path = "/view-employee-count-by-department", 
			method = RequestMethod.GET)
	public String viewEmployeeCountByDepartment (Model model) 
	{
		String message = "Employee Count For Each Department";

		List<String> columnLabel = new ArrayList<>();
		LinkedHashMap<Department, Integer> records = new LinkedHashMap<>();

		// Get column labels and employee counts by department
		this.departmentService.getEmployeeCountByDepartment(columnLabel, records);

		model.addAttribute("informationType", message);
		model.addAttribute("columnLabel", columnLabel);
		model.addAttribute("records", records);

		return "view-employee-count-by-department";
	}
}
