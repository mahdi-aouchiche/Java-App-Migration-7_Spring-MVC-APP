package com.demo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.Department;
import com.demo.entity.Employee;

@Repository("employeeRepository")
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {
	public final SessionFactory sessionFactory;


	/**
	 * Constructor
	 * @param sessionFactory
	 */
	@Autowired
	public EmployeeDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	/**
	 * Add a new employee to the database
	 * @param newEmployee: the new employee to add
	 */
	@Override
	public void addEmployee(Employee newEmployee) {
		Session session = sessionFactory.getCurrentSession();
		session.save(newEmployee);
	}


	/**
	 * Update employee info
	 * @param employeeID
	 * @param name
	 * @param age
	 * @param salary
	 * @return number of employees updated
	 */
	@Override
	public int updateEmployee(int employeeID, String name, int age, double salary)
	{
		Session session = sessionFactory.getCurrentSession();

		Employee employee = session.get(Employee.class, employeeID);
		if(employee != null) {
			employee.setName(name);
			employee.setAge(age);
			employee.setSalary(salary);
			session.update(employee);
			return 1;
		}

		return 0;
	}


	/**
	 * Delete an employee
	 * @param employeeID to delete
	 * @return true if employee is deleted. false otherwise.
	 */
	@Override
	public Boolean deleteEmployee(int employeeID) {

		Session session = sessionFactory.getCurrentSession();

		// Retrieve the employee from database
		Employee employeeToDelete = session.get(Employee.class, employeeID);

		if(employeeToDelete != null) {
			// Clear all departments associations
			employeeToDelete.getDepartments().clear();

			// Delete the Employee
			session.delete(employeeToDelete);

			return true;
		}
        return false; // Return false if not found
	}


	/**
	 * Add an existing employee to a department
	 * @param Department id
	 * @param Employee id
	 * @return -1: unsuccessful,
	 *        	0: employee is already assigned to the department
	 *          1: successfully added employee to department
	 */
	@Override
	public int addEmployeeToDepartment(int departmentId, int employeeId) {
		Session session = sessionFactory.getCurrentSession();
		
	    // Retrieve employee and department objects
	    Employee employee = session.get(Employee.class, employeeId);
	    Department department = session.get(Department.class, departmentId);

	    int result;
	    if(employee == null || department == null) {
	    	// Employee or Department not found
	    	result = -1;
	    }

	    // Check if the employee is associated to the department already
	    if(employee.getDepartments().contains(department)) {
	    	// Employee is associated to the department already
	    	result = 0; 
	    } else {
	    	employee.addDepartment(department);
	    	session.persist(employee);
			
	    	// Employee associated to the department successfully
	    	result = 1;
	    }
	    
	    return result;
	}


	/**
	 * Get all employees sorted by name
	 * @return list of all employees
	 */
	@Override
	public List<Employee> getAllEmployees() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("From Employee ORDER BY name", Employee.class)
					  .setCacheable(true)
					  .getResultList();
	}


	/**
	 * Get the list of employees associated to a department
	 * @param List of employees who are associated to a department
	 */
	@Override
	public void getEmployeesAssociatedToDepartment(
			List<Employee> employeesAssociatedToDepartment)
	{
		Session session = sessionFactory.getCurrentSession();

		// Query to get the employees list
		String hql = "SELECT e FROM Employee e WHERE e.departments IS NOT EMPTY";

		// Clear if anyhting is on the list
		employeesAssociatedToDepartment.clear();

		// Add all the results (make sure we get a list of employees)
		employeesAssociatedToDepartment.addAll(
				session.createQuery(hql, Employee.class)
					   .setCacheable(true)
					   .getResultList()
			);
	}


	/**
	 * Get the list of employees associated to a department
	 * @param List of employees who are NOT associated to a department
	 */
	@Override
	public void getEmployeesNotAssociatedToDepartment(
			List<Employee> employeesNotAssociatedToDepartment)
	{
		Session session = sessionFactory.getCurrentSession();

		// Query to get the employees list
		String hql = "SELECT e FROM Employee e WHERE e.departments IS EMPTY";

		// Clear if anyhting is on the list
		employeesNotAssociatedToDepartment.clear();

		// Add all the results (make sure we get a list of employees)
		employeesNotAssociatedToDepartment.addAll(
			session.createQuery(hql, Employee.class)
				   .setCacheable(true)
				   .getResultList());
	}



	/**
	 * Get the average salary of all employees
	 * @return average salary as a double value.
	 */
	@Override
	public double employeesAverageSalary() {

		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COALESCE(AVG(salary), 0) FROM Employee";

		return session.createQuery(hql, Double.class)
					  .setCacheable(true)
			     	  .getSingleResult();
	}


	/**
	 * Get maximum and minimum salaries
	 * @return [minimum salary, maximum salary]
	 */
	@Override
	public List<Double>  getMinAndMaxSalary() {

		Session session = sessionFactory.getCurrentSession();
		List<Double> minAndMaxSalary = new ArrayList<>();
		String hql = "SELECT COALESCE(MIN(salary), 0), COALESCE(MAX(salary), 0) FROM Employee";

		Object result[] = session.createQuery(hql, Object[].class)
								 .setCacheable(true)
								 .getSingleResult();

		minAndMaxSalary.addAll(Arrays.asList((Double)result[0], (Double)result[1]));

		return minAndMaxSalary;
	}


	/**
	 * Get Second maximum salary
	 * @return Second maximum salary
	 */
	@Override
	public double getSecondMaximumSalary() {

		Session session = sessionFactory.getCurrentSession();

		String hql = "SELECT COALESCE(MAX(salary), 0) "
				   + "FROM Employee "
				   + "WHERE salary < (SELECT MAX(salary) FROM Employee)";

		return session.createQuery(hql, Double.class)
					  .setCacheable(true)
					  .getSingleResult();
	}


	/**
	 * Get the list of employees earning the second highest salary
	 * @param list which will hold the employees earning second max salary
	 */
	@Override
	public void getEmployeesEarningSecondMaximumSalary(
			List<Employee> employeesEarningSecondMaximumSalary)
	{
		Session session = sessionFactory.getCurrentSession();

		// Query to get the list of employees
		String hql =  "FROM Employee WHERE salary = ( "
				   +  "    SELECT MAX(salary) FROM Employee WHERE salary < ( "
				   +  "        SELECT MAX(salary) FROM Employee "
				   +  "    )"
				   +  ")";

		// Clear the list
		employeesEarningSecondMaximumSalary.clear();

		employeesEarningSecondMaximumSalary.addAll(
				session.createQuery(hql, Employee.class)
					   .setCacheable(true)
					   .getResultList());
	}
}
