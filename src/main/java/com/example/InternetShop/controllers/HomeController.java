package com.example.InternetShop.controllers;

import com.example.InternetShop.models.Product;
import com.example.InternetShop.services.CategoryService;
import com.example.InternetShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"/","/index","/home"})
    public String home(Model model){
        model.addAttribute("products", getAllProducts());
        model.addAttribute("productsCount", productsCount());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @RequestMapping("/searchByCategory")
    public String homePost(@RequestParam("categoryId") long categoryId, Model model){
        model.addAttribute("products", productService.findAllByCategoryId(categoryId));
        model.addAttribute("productsCount", productService.count());
        model.addAttribute("categories", categoryService.findAll());
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    private List<Product> getAllProducts(){
        return productService.findAllByOrderByIdAsc();
    }

    private long productsCount(){
        return productService.count();
    }
}

