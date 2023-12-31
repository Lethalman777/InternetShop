package com.example.InternetShop.controllers;

import com.example.InternetShop.models.Product;
import com.example.InternetShop.services.CategoryService;
import com.example.InternetShop.services.ProductService;
import com.example.InternetShop.validators.ProductValidator;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductValidator productValidator;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, ProductValidator productValidator, CategoryService categoryService) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.categoryService = categoryService;
    }

    @GetMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new Product());
        model.addAttribute("method", "new");
        model.addAttribute("categories", categoryService.findAll());
        return "product";
    }

    @GetMapping("/product/detail/{id}")
    public String productDetail(@PathVariable("id") long productId, Model model) {
        Product product = productService.findById(productId);
        if (product != null){
            model.addAttribute("product", product);
            return "productDetail";
        }else {
            return "error/404";
        }
    }

    @PostMapping("/product/new")
    public String newProduct(@ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model) {
        productValidator.validate(productForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "new");
            return "product";
        }
        productService.save(productForm);
        log.info(String.format("Product with id: %s successfully created.", productForm.getId()));
        return "redirect:/home";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") long productId, Model model){

        Product product = productService.findById(productId);
        if (product != null){
            model.addAttribute("productId", productId);
            model.addAttribute("productForm", product);
            model.addAttribute("method", "edit");
            model.addAttribute("categories", categoryService.findAll());
            return "productEdit";
        }else {
            return "error/404";
        }
    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") long productId, @ModelAttribute("productForm") Product productForm, BindingResult bindingResult, Model model){
        productValidator.validate(productForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error(String.valueOf(bindingResult.getFieldError()));
            model.addAttribute("method", "edit");
            return "productEdit";
        }
        productService.edit(productId, productForm);
        log.info(String.format("Product with id: %s has been successfully edited.", productId));

        return "redirect:/home";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId){
        log.info(String.valueOf(productId));
        Product product = productService.findById(productId);
        if (product != null){
            productService.delete(productId);
            log.info(String.format("Product with id: %s successfully deleted.", product.getId()));
            return "redirect:/home";
        }else {
            return "error/404";
        }
    }
}

