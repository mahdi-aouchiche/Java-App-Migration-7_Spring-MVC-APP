package com.demo.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.EmployeeDao;
import com.demo.entity.Employee;
import com.demo.exceptions.EmployeeAlreadyExistsException;


@Service("employeeService")
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;


	/**
	 * Delete an employee from the database
	 * @param employeeID
	 * @return true if employee is deleted, false otherwise.
	 */
	public Boolean deleteEmployee(int employeeID) {
		try {
			return this.employeeDao.deleteEmployee(employeeID);
		} catch (Exception e) {
			System.err.println("Error deleting employee: " + e.getMessage());
			return false;
		}
	}


	/**
	 * Add a new employee
	 * @param firstname employee's firstname
	 * @param lastname  employee's lastname
	 * @param age       employee's age
	 * @param salary    employee's salary
	 */
	public void addNewEmployee(String firstname, String lastname, int age, double salary) {
		// Name formating
		String fullname = firstname.trim() + " " + lastname.trim();
		String[] splitName = fullname.trim().split("\\s+");

		String name = "";
		for (String str : splitName) {
			if(str.isEmpty()) {
				continue;
			}
			str = str.toLowerCase();
			name += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		}
		name = name.trim();

		// Create a new employee
		Employee newEmployee = new Employee(name, age, salary);

		try {
			// employee added
			this.employeeDao.addEmployee(newEmployee);
		} catch (ConstraintViolationException e) {
	        // employee exists
			throw new EmployeeAlreadyExistsException(name);
		} catch (Exception e) {
			// an error occured
			throw new RuntimeException("Unsuccessful, an error occured.");
		}
	}


	/**
	 * Update employee info
	 * @param employeeID
	 * @param name
	 * @param age
	 * @param salary
	 * @return number of updated records in the DB.
	 */
	public int updateEmployee(int employeeID, String name, int age, double salary) {

		// Name formating
		String[] splitName = name.trim().split("\\s+");

		String fullname = "";
		for(String str : splitName) {
			if(str.isEmpty()) continue;
			
			str = str.toLowerCase();
			fullname += str.substring(0, 1).toUpperCase() + str.substring(1) + " ";
		}
		fullname = fullname.trim();

		try {
			return this.employeeDao.updateEmployee(employeeID, fullname, age, salary);
		} catch (Exception e) {
			System.err.println("Error updating employee: " + e.getMessage());
		}
		return 0;
	}


	/**
	 * List of all employees sorted by name
	 * @param columnLabels
	 * @param allEmployees
	 */
	public void listOfAllEmployees(
			List<String> columnLabels,
			List<Employee> allEmployees)
	{
		// Set the column labels
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");

		// Get the list of all employees
		allEmployees.addAll(this.employeeDao.getAllEmployees());
	}


	/**
	 * Find all employees affiliated to a department
	 * Sorted by Name Ascending order
	 * @param List of column labels
	 * @param List of employees associated to a department
	 */
	public void listOfEmployeesAssociatedToDepartment(
			List<String> columnLabels,
			List<Employee> employeesAssociatedToDepartment)
	{
		// Set the column lables
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");

		// Add the action column (delete and edit) an employee
		columnLabels.add("Action");

		// List of employees associated to a department
		this.employeeDao
		    .getEmployeesAssociatedToDepartment(
		    		employeesAssociatedToDepartment
		    );

		// sort the list by employee name
		Collections.sort(
				employeesAssociatedToDepartment,
				(e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName())
		);
	}


	/**
	 * Find all employees NOT affiliated to a department
	 * @param List of column labels
	 * @param List of employees NOT associated to a department
	 */
	public void listOfEmployeesNotAssociatedToDepartment(
			List<String> columnLabels,
			List<Employee> employeesNotAssociatedToDepartment)
	{
		// Set the column lables
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");
		// Add the action column to delete and edit an employee
		columnLabels.add("Action");

		// List of employees not associated to a department
		this.employeeDao
			.getEmployeesNotAssociatedToDepartment(
					employeesNotAssociatedToDepartment
			);

		// sort the list by employee name
		Collections.sort(
				employeesNotAssociatedToDepartment,
				(e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName())
		);
	}


	/**
	 * Add an employee to a department
	 * @param Department ID
	 * @param Employee ID
	 * @return -1: unsuccessful,
	 *        	0: employee is already assigned to the department
	 *          1: successfully added employee to department
	 */
	public int addEmployeeToDepartment(int departmentId, int employeeId){

		int result = -1;
		try {
			result = this.employeeDao.addEmployeeToDepartment(departmentId,employeeId);
		} catch (Exception e) {
			System.out.println("Error associating employee to department: " + e.getMessage());
		}
		
		return result;
	}


	/**
	 * Get all employees
	 * @return list of employees sorted by name
	 */
	public List<Employee> getEmployeeList() {
		return this.employeeDao.getAllEmployees();
	}


	/**
	 * Find the Average salary of all employees
	 * @return The average salary of all employees
	 */
	public double getEmployeesAverageSalary() {
		return this.employeeDao.employeesAverageSalary();
	}


	/**
	 * Find the Maximum and Minimum salaries of employees
	 * @return [minimum salary, maximum salary]
	 */
	public List<Double> getMinAndMaxSalary() {
		return this.employeeDao.getMinAndMaxSalary();
	}


	/**
	 * Find the second Max salary
	 * @return The second max salary
	 */
	public double secondMaxEmployeesSalary() {
		return this.employeeDao.getSecondMaximumSalary();
	}


	/**
	 * Find the employees earning the second maximum salary
	 * @param List of column labels
	 * @param List of employees earning the second maximum salary
	 *        sorted by name ascending
	 */
	public void employeesEarningSecondMaximumSalary(
			List<String> columnLabels,
			List<Employee> employeesEarningSecondMaximumSalary)
	{
		// Set the column labels
		columnLabels.add("Employee ID");
		columnLabels.add("Name");
		columnLabels.add("Age");
		columnLabels.add("Salary");

		// List of employees earning second highest salary
		this.employeeDao.getEmployeesEarningSecondMaximumSalary(
				employeesEarningSecondMaximumSalary
		);

		// Sort the list by name
		Collections.sort(
			employeesEarningSecondMaximumSalary,
			(e1,e2) -> e1.getName().compareToIgnoreCase(e2.getName())
		);
	}
}
