package com.demo.exceptions;

public class EmployeeAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeAlreadyExistsException(String employeeName) {
		super("Employee \"" + employeeName + "\" Exists Already!");
	}
}
