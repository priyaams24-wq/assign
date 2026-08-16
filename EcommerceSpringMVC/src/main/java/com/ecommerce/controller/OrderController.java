package com.ecommerce.controller;

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
    public ModelAndView checkout(@RequestParam String productNo, @RequestParam String customerId) {
        Optional<ProductResponse> productOpt = productService.getProductByNo(productNo);
        if (!productOpt.isPresent()) {
            return new ModelAndView("redirect:/webmvc/products/list?customerId=" + customerId);
        }
        ModelAndView mav = new ModelAndView("checkout");
        mav.addObject("product", productOpt.get());
        mav.addObject("customerId", customerId);
        return mav;
    }

    @PostMapping("/place")
    public ModelAndView placeOrder(@RequestParam String productNo,
                                   @RequestParam int quantity,
                                   @RequestParam String customerId) {
        try {
            OrderResponse order = orderService.placeOrder(customerId, productNo, quantity);
            ModelAndView mav = new ModelAndView("receipt");
            mav.addObject("order", order);
            mav.addObject("quantity", quantity);
            mav.addObject("customerId", customerId);
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
            mav.addObject("customerId", customerId);
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @GetMapping("/history")
    public ModelAndView history(@RequestParam String customerId,
                                @RequestParam(required = false) String fromDate,
                                @RequestParam(required = false) String toDate,
                                @RequestParam(required = false) String afterDate,
                                @RequestParam(required = false) String beforeDate) {

        LocalDate from = (fromDate != null && !fromDate.trim().isEmpty()) ? LocalDate.parse(fromDate.trim()) : null;
        LocalDate to = (toDate != null && !toDate.trim().isEmpty()) ? LocalDate.parse(toDate.trim()) : null;
        LocalDate after = (afterDate != null && !afterDate.trim().isEmpty()) ? LocalDate.parse(afterDate.trim()) : null;
        LocalDate before = (beforeDate != null && !beforeDate.trim().isEmpty()) ? LocalDate.parse(beforeDate.trim()) : null;

        List<OrderDetailsResponse> history;
        if (from != null && to != null) {
            history = orderService.getCustomerOrderHistoryBetween(customerId, from, to);
        } else if (after != null) {
            history = orderService.getCustomerOrderHistoryAfter(customerId, after);
        } else if (before != null) {
            history = orderService.getCustomerOrderHistoryBefore(customerId, before);
        } else {
            history = orderService.getCustomerOrderHistory(customerId);
        }

        ModelAndView mav = new ModelAndView("history");
        mav.addObject("history", history);
        mav.addObject("customerId", customerId);
        mav.addObject("fromDate", fromDate);
        mav.addObject("toDate", toDate);
        mav.addObject("afterDate", afterDate);
        mav.addObject("beforeDate", beforeDate);
        return mav;
    }

    @GetMapping("/invoice")
    public ModelAndView generateInvoice(@RequestParam String customerId) {
        try {
            InvoiceResponse invoice = orderService.generateCustomerInvoice(customerId);
            ModelAndView mav = new ModelAndView("invoice");
            mav.addObject("invoice", invoice);
            mav.addObject("customerId", customerId);
            return mav;
        } catch (Exception e) {
            // If error, redirect back to portal
            return new ModelAndView("redirect:/webmvc/customer/portal?customerId=" + customerId + "&error=" + e.getMessage());
        }
    }
}
