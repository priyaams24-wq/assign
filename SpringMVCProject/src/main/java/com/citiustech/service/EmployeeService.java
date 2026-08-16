package com.citiustech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.model.Employee;
import com.citiustech.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public void saveEmployee(Employee employee) {
		employeeRepository.saveEmployee(employee);
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.getAllEmployees();
	}
}
