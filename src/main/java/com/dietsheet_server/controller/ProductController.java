package com.dietsheet_server.controller;

import com.dietsheet_server.model.diet.Product;
import com.dietsheet_server.model.User;
import com.dietsheet_server.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    Service<Product> productService;

    @GetMapping(value = "/product")
    public ResponseEntity<List<Product>> getAllProducts(@AuthenticationPrincipal User user) {
        List<Product> products = productService.findAll(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(
            @PathVariable("id") long id) {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @AuthenticationPrincipal User user) {
        productService.save(product, user);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping(value = "/product/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") long id,
            @RequestBody Product productUpdateData) {
        productService.update(productUpdateData, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/product")
    public ResponseEntity<Product> deleteAllProducts() {
        productService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

