package com.ecommerce.controller;

import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list/{customerId}")
    public ModelAndView listProducts(@PathVariable String customerId,
                                     @RequestParam(required = false) String filter,
                                     @RequestParam(required = false) Double minPrice,
                                     @RequestParam(required = false) Double maxPrice,
                                     @RequestParam(required = false) Integer minStock) {

        List<ProductResponse> products;
        if ("available".equals(filter)) {
            products = productService.getAvailableProducts();
        } else if (minPrice != null && maxPrice != null) {
            products = productService.getProductsByPriceRange(minPrice, maxPrice);
        } else if (minStock != null) {
            products = productService.getProductsByMinStock(minStock);
        } else {
            products = productService.getAllProducts();
        }

        ModelAndView mav = new ModelAndView("products");
        mav.addObject("products", products);
        mav.addObject("customerId", customerId);
        mav.addObject("filter", filter);
        mav.addObject("minPrice", minPrice);
        mav.addObject("maxPrice", maxPrice);
        mav.addObject("minStock", minStock);
        return mav;
    }
}
