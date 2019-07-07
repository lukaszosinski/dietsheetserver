package com.dietsheet_server.service;


import com.dietsheet_server.DAO.ProductDAO;
import com.dietsheet_server.model.Product;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service("productService")
@Transactional
public class ProductServiceImpl implements Service<Product> {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public Product findById(long id) {
        Product product = productDAO.get(id);
        if(product == null) {
            return null;
        }
        Hibernate.initialize(product.getProductDetails());
        return product;
    }

    @Override
    public Product findByName(String name) {
        return null;
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
    public List<Product> findAll() {
        return productDAO.getAll();
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
