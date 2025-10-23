 package com.demo.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.DepartmentDao;
import com.demo.entity.Department;
import com.demo.exceptions.DepartmentAlreadyExistsException;

@Service("departmentService")
public class DepartmentService {

	@Autowired
	DepartmentDao departmentDao;


	/**
	 * Create a new Department in the database
	 * @param department name
	 */
	public void createNewDepartment(String departmentName) {

		if(departmentName == null || departmentName.trim().isEmpty()) {
			throw new IllegalArgumentException("Department Name Cannot Be Empty.");
		}

		String dName = "";
		String[] splitName = departmentName.trim().split("\\s+");

		for(String str : splitName) {
			str = str.toLowerCase();
			dName += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		}
		dName = dName.trim();

		try {
			// Add department
			this.departmentDao.createDepartment(new Department(dName));
		} catch (ConstraintViolationException e) {
			// Department name exists already
			throw new DepartmentAlreadyExistsException(dName);
		} catch (Exception e) {
			// Error adding department
			throw new RuntimeException ("Error. Enable To Add Department: \"" + dName + "\".");
		}
	}


	/**
	 * Delete a Department
	 *
	 * @param departmentId
	 * @return true if department is deleted, false otherwise.
	 */
	public boolean deleteDepartment(int departmentId) {
		return this.departmentDao.deleteDepartment(departmentId);
	}


	/**
	 * Get all departments
	 *
	 * @return list of departments sorted by name
	 */
	public List<Department> getDepartmentList() {
		return this.departmentDao.getAllDepartments();
	}


	/**
	 * Get employee count for each department
	 *
	 * @param List of column labels
	 * @param LinkedHashMap of employee count of each department
	 */
	public void getEmployeeCountByDepartment(
			List<String> columnLabel,
			LinkedHashMap<Department, Integer> records)
	{
		// Set the column labels
		columnLabel.add("Department ID");
		columnLabel.add("Department Name");
		columnLabel.add("Number of Employees");

		// Get the employee count for each department
		records.putAll(
				this.departmentDao
				.employeeCountByDepartment()
		);
	}


	/**
	 * Find employees average salary in each department
	 *
	 * @param List to hold the column labels
	 * @Return map to hold the mapping of employee average salary to each department
	 */
	public LinkedHashMap<Department, Double>
		getAverageSalaryByDepartment(List<String> columnLabel)
	{
		// Set the column labels
		columnLabel.add("Department ID");
		columnLabel.add("Department Name");
		columnLabel.add("Average Salary");

		//Get the average salary vor each department
		return this.departmentDao
				   .averageSalaryByDepartment();
	}


	/**
	 * Find all departments with a minimum given number of employees
	 *
	 * @param minimum number of employees to consider in the result
	 * @param list to hold the table header column labels
	 * @param map to hold the mapping of employee count of each department
	 * @return A list of all departments with at least numEmployees
	 */
	public void departmentsWithAtLeastACertainNumberOfEmployees(
			long numEmployees,
			List<String> columnLabel,
			LinkedHashMap<Department, Integer> records)
	{
		// Set column labels
		columnLabel.add("Department ID");
		columnLabel.add("Department Name");
		columnLabel.add("Number Of Employees");

		// Get the department list with at least numEmployees
		for(Object[] row : this.departmentDao.departmentsByNumberOfEmployees(numEmployees)) {
			records.put((Department)row[0], ((Long) row[1]).intValue());
		}
	}
}
