package com.citiustech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller //@Component
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Welcome to Spring MVC");
		modelAndView.setViewName("hello");
		return modelAndView;
	}
	
	@GetMapping("/requestParameters")
	//@RequestMapping(name = "/requestParameters", method = RequestMethod.GET)
	public ModelAndView handleRequestParameters(@RequestParam String username, @RequestParam int age) {
//		String message = """
//					Hello user %s your age is %d
//				""".formatted(username, age);
		String message = String.format("Hello user %s your age is %d", username, age);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.setViewName("hello");
		
		return modelAndView;
	}
	
	@GetMapping("/pathVariables/{username}/{age}")
	public ModelAndView handlePathVariables(@PathVariable String username, @PathVariable int age) {
//		String message = """
//					Hello user %s your age is %d
//				""".formatted(username, age);
		String message = String.format("Hello user %s your age is %d", username, age);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.setViewName("hello");
		
		return modelAndView;
	}
}



