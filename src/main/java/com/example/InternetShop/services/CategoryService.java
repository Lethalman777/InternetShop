package com.example.InternetShop.services;

import com.example.InternetShop.models.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);
    List<Category> findAll();
}
