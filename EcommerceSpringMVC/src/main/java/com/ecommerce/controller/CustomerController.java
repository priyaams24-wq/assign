package com.ecommerce.controller;

import com.ecommerce.dto.CustomerRegisterRequest;
import com.ecommerce.dto.CustomerResponse;
import com.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public ModelAndView showLoginPage(HttpSession session) {
        if (session.getAttribute("loggedInCustomer") != null) {
            return new ModelAndView("redirect:/customer/portal");
        }
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<CustomerResponse> optCustomer = customerService.login(email, password);
        if (optCustomer.isPresent()) {
            session.setAttribute("loggedInCustomer", optCustomer.get());
            return new ModelAndView("redirect:/customer/portal");
        }
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", "Invalid email or password");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage(HttpSession session) {
        if (session.getAttribute("loggedInCustomer") != null) {
            return new ModelAndView("redirect:/customer/portal");
        }
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

    @GetMapping("/portal")
    public ModelAndView showPortal(HttpSession session) {
        CustomerResponse loggedIn = (CustomerResponse) session.getAttribute("loggedInCustomer");
        if (loggedIn == null) {
            return new ModelAndView("redirect:/customer/login");
        }
        Optional<CustomerResponse> refreshed = customerService.getCustomerById(loggedIn.customerId());
        if (refreshed.isPresent()) {
            session.setAttribute("loggedInCustomer", refreshed.get());
            loggedIn = refreshed.get();
        }
        ModelAndView mav = new ModelAndView("portal");
        mav.addObject("customer", loggedIn);
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute("loggedInCustomer");
        session.invalidate();
        return new ModelAndView("redirect:/customer/login");
    }
}
