package com.ecommerce.controller;

import com.ecommerce.dto.CustomerRegisterRequest;
import com.ecommerce.dto.CustomerResponse;
import com.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        Optional<CustomerResponse> optCustomer = customerService.login(email, password);
        if (optCustomer.isPresent()) {
            return new ModelAndView("redirect:/webmvc/customer/portal/" + optCustomer.get().customerId());
        }
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", "Invalid email or password");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String customerId, 
                                 @RequestParam String email, 
                                 @RequestParam String password, 
                                 @RequestParam double credit) {
        try {
            CustomerRegisterRequest request = new CustomerRegisterRequest(customerId, password, email, credit);
            customerService.register(request);
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("message", "Registration successful! Please login.");
            return mav;
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView("register");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @GetMapping("/portal/{customerId}")
    public ModelAndView showPortal(@PathVariable String customerId) {
        Optional<CustomerResponse> refreshed = customerService.getCustomerById(customerId);
        if (!refreshed.isPresent()) {
            return new ModelAndView("redirect:/webmvc/customer/login");
        }
        ModelAndView mav = new ModelAndView("portal");
        mav.addObject("customer", refreshed.get());
        mav.addObject("customerId", customerId);
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("redirect:/webmvc/customer/login");
    }
}
