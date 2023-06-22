package com.example.InternetShop;

import com.example.InternetShop.models.Category;
import com.example.InternetShop.models.Product;
import com.example.InternetShop.models.User;
import com.example.InternetShop.repositories.CategoryRepository;
import com.example.InternetShop.services.ProductService;
import com.example.InternetShop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class StartupData implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public StartupData(UserService userService, ProductService productService, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        adminAccount();
        userAccount();
        category();
        exampleProducts();
    }

    private void userAccount(){
        User user = new User();

        user.setUsername("user");
        user.setPassword("user");
        user.setPasswordConfirm("user");
        user.setGender("Female");
        user.setEmail("user@example.com");
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setCity("Warszawa");

        userService.save(user);
    }

    private void adminAccount(){
        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setGender("Male");
        admin.setEmail("admin@example.com");
        admin.setFirstName("Adam");
        admin.setLastName("Nowak");
        admin.setCity("Warszawa");

        userService.save(admin);
    }

    private void category(){
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();
        Category category4 = new Category();

        category1.setCategoryName("RTV AGD");
        category2.setCategoryName("Jedzenie");
        category3.setCategoryName("Meble");
        category4.setCategoryName("Gry Komputerowe");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
    }

    private void exampleProducts(){
        final String NAME = "Example Name";
        final String DESCRIPTION = "Example Description";
        final BigDecimal PRICE = BigDecimal.valueOf(22);

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        product1.setName("Pralka");
        product1.setDescription("pierze pranie");
        product1.setCategory(categoryRepository.findByCategoryName("RTV AGD"));
        product1.setPrice(BigDecimal.valueOf(350));

        product2.setName("Cheeseburger");
        product2.setDescription("dobrze smakuje");
        product2.setCategory(categoryRepository.findByCategoryName("Jedzenie"));
        product2.setPrice(BigDecimal.valueOf(22));

        product3.setName("Stół");
        product3.setDescription("duży blat");
        product3.setCategory(categoryRepository.findByCategoryName("Meble"));
        product3.setPrice(BigDecimal.valueOf(150));

        product4.setName("Wiedźmin 3");
        product4.setDescription("fajna gra");
        product4.setCategory(categoryRepository.findByCategoryName("Gry Komputerowe"));
        product4.setPrice(BigDecimal.valueOf(60));

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);
        productService.save(product4);
    }
}

