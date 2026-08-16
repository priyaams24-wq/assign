package com.ecommerce.controller;

import com.ecommerce.dto.CustomerResponse;
import com.ecommerce.dto.InvoiceResponse;
import com.ecommerce.dto.OrderDetailsResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/checkout")
    public ModelAndView checkout(@RequestParam String productNo, HttpSession session) {
        if (session.getAttribute("loggedInCustomer") == null) {
            return new ModelAndView("redirect:/customer/login");
        }
        Optional<ProductResponse> productOpt = productService.getProductByNo(productNo);
        if (!productOpt.isPresent()) {
            return new ModelAndView("redirect:/products/list");
        }
        ModelAndView mav = new ModelAndView("checkout");
        mav.addObject("product", productOpt.get());
        return mav;
    }

    @PostMapping("/place")
    public ModelAndView placeOrder(@RequestParam String productNo,
                                   @RequestParam int quantity,
                                   HttpSession session) {
        CustomerResponse loggedIn = (CustomerResponse) session.getAttribute("loggedInCustomer");
        if (loggedIn == null) {
            return new ModelAndView("redirect:/customer/login");
        }
        try {
            OrderResponse order = orderService.placeOrder(loggedIn.customerId(), productNo, quantity);
            ModelAndView mav = new ModelAndView("receipt");
            mav.addObject("order", order);
            mav.addObject("quantity", quantity);
            Optional<ProductResponse> prodOpt = productService.getProductByNo(productNo);
            prodOpt.ifPresent(p -> {
                mav.addObject("product", p);
                mav.addObject("amount", p.price() * quantity);
            });
            return mav;
        } catch (Exception e) {
            Optional<ProductResponse> prodOpt = productService.getProductByNo(productNo);
            ModelAndView mav = new ModelAndView("checkout");
            prodOpt.ifPresent(p -> mav.addObject("product", p));
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @GetMapping("/history")
    public ModelAndView history(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate afterDate,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beforeDate,
                                HttpSession session) {
        CustomerResponse loggedIn = (CustomerResponse) session.getAttribute("loggedInCustomer");
        if (loggedIn == null) {
            return new ModelAndView("redirect:/customer/login");
        }

        List<OrderDetailsResponse> history;
        if (fromDate != null && toDate != null) {
            history = orderService.getCustomerOrderHistoryBetween(loggedIn.customerId(), fromDate, toDate);
        } else if (afterDate != null) {
            history = orderService.getCustomerOrderHistoryAfter(loggedIn.customerId(), afterDate);
        } else if (beforeDate != null) {
            history = orderService.getCustomerOrderHistoryBefore(loggedIn.customerId(), beforeDate);
        } else {
            history = orderService.getCustomerOrderHistory(loggedIn.customerId());
        }

        ModelAndView mav = new ModelAndView("history");
        mav.addObject("history", history);
        mav.addObject("fromDate", fromDate);
        mav.addObject("toDate", toDate);
        mav.addObject("afterDate", afterDate);
        mav.addObject("beforeDate", beforeDate);
        return mav;
    }

    @GetMapping("/invoice")
    public ModelAndView generateInvoice(HttpSession session) {
        CustomerResponse loggedIn = (CustomerResponse) session.getAttribute("loggedInCustomer");
        if (loggedIn == null) {
            return new ModelAndView("redirect:/customer/login");
        }
        try {
            InvoiceResponse invoice = orderService.generateCustomerInvoice(loggedIn.customerId());
            ModelAndView mav = new ModelAndView("invoice");
            mav.addObject("invoice", invoice);
            return mav;
        } catch (Exception e) {
            ModelAndView mav = new ModelAndView("portal");
            mav.addObject("customer", loggedIn);
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }
}
