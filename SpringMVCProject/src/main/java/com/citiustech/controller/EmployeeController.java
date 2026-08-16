package com.citiustech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.citiustech.model.Employee;
import com.citiustech.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public ModelAndView initialize() {
		ModelAndView modelAndView = new ModelAndView();
		Employee employee = new Employee();
		modelAndView.addObject("employee", employee);
		modelAndView.setViewName("emp");
		return modelAndView;
	}
	
	@PostMapping
	public ModelAndView submitEmployee(@ModelAttribute Employee employee) {
		System.out.println(employee);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("emp");
		employeeService.saveEmployee(employee);
		return modelAndView;
	}
}




