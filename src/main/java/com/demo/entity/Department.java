package com.demo.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(	name = "department")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "departmentCache")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    // Maps Many-to-Many relationship
    // mappedBy="departments" marks this as the inverse side (replaces inverse="true")
    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    @OrderColumn(name = "list_index")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Employee> employees;


	/**
	 * Default Constructor
	 */
	public Department() {}


	/**
	 * @param name
	 */
	public Department(String name) {
		super();
		this.name = name;
	}


	/**
	 * @param id
	 * @param name
	 */
	public Department(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return department's name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Get employees of this department
	 * @return list of employees affiliated to the department
	 */
	public List<Employee> getEmployees(){
		return employees;
	}


	/**
	 * Set the employee list affiliated to the department
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}


	/**
	 * Helper method to add an employee
	 * @param employee
	 */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getDepartments().add(this);
    }


	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Department other = (Department) obj;
		return id == other.id && Objects.equals(name, other.name);
	}


	@Override
	public String toString() {
		return "Department [ID=" + id + ", Name=" + name + "]";
	}
}
