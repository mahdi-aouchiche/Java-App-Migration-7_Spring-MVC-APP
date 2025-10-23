package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entity.Department;
import com.demo.service.DepartmentService;

@Controller
public class DeleteADepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(
			path= "/delete-a-department", 
			method = RequestMethod.GET)
	public String getDepartmentToDelete(Model model)
	{
		List<Department> departments = this.departmentService
										   .getDepartmentList();
		
		model.addAttribute("departments", departments);
		return "delete-a-department";
	}
	
	@RequestMapping(
			path = "/delete-a-department", 
			method = RequestMethod.POST)
	public String deleteADepartment(
			Model model,
			@RequestParam("departmentId") Integer departmentId) 
	{
		if(this.departmentService.deleteDepartment(departmentId)) {
			model.addAttribute(
					"successMessage", 
					"Department ID = " + departmentId + ". Delete Successful!");
		} else {
			model.addAttribute(
					"errorMessage", 
					"Department ID = " + departmentId + ". Delete Unsuccessful!");
		}
		
		List<Department> departments = this.departmentService
				   						   .getDepartmentList();

		model.addAttribute("departments", departments);
		
		return "delete-a-department";
	}
}
