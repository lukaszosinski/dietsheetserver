package com.dietsheet_server.service;


import com.dietsheet_server.DAO.ProductDAO;
import com.dietsheet_server.model.Product;
import com.dietsheet_server.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("productService")
@Transactional
public class ProductServiceImpl implements Service<Product>{

    @Autowired
    private ProductDAO productDAO;


    @Override
    @PostAuthorize("returnObject.getOwner().getUsername() == principal.getUsername()")
    public Product findById(long id) {
        Product product = productDAO.get(id);
        if(product == null) {
            throw new ResourceNotFoundException();
        }
        Hibernate.initialize(product.getProductDetails());
        return product;
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public void update(Product product) {
        productDAO.update(product);
    }

    @Override
    public void deleteById(long id) {
        productDAO.delete(id);
    }

    @Override
    public void delete(Product product) {
        productDAO.delete(product);
    }

    @Override
    public List<Product> findAll() {
        return productDAO.getAll();
    }

    @Override
    public List<Product> findAll(User user) {
        return productDAO.getAllByUser(user);
    }

    @Override
    public void deleteAll() {
        productDAO.deleteAll();
    }

    @Override
    public boolean isExist(Product product) {
        return productDAO.get(product.getId()) != null;
    }
}
