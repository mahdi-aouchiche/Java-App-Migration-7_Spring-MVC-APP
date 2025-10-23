package com.demo.exceptions;

public class DepartmentAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DepartmentAlreadyExistsException(String departmentName) {
		super("Department \"" + departmentName + "\" Exists Already!");
	}
}