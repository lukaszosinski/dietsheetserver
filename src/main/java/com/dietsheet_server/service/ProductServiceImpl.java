package com.dietsheet_server.service;


import com.dietsheet_server.DAO.ProductDAO;
import com.dietsheet_server.model.diet.Product;
import com.dietsheet_server.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Product findById(long id) {
        Product product = productDAO.get(id);
        if(product == null) {
            throw new ResourceNotFoundException();
        }
        return product;
    }

    @Override
    public void save(Product product) {
        if(isExist(product)) {
            throw new DataIntegrityViolationException("Resource exists");
        }
        productDAO.save(product);
    }

    @Override
    public void save(Product product, User owner) {
        product.setOwner(owner);
        save(product);
    }

    @Override
    public void update(Product productUpdateData, long id) {
        Product productToUpdate = findById(id);
        productToUpdate.setName(productUpdateData.getName());
        productToUpdate.updateSummary(productUpdateData.getSummary());
        productDAO.update(productToUpdate);
    }

    @Override
    public void delete(Product product) {
        productDAO.delete(product);
    }

    @Override
    public void delete(long id) {
        Product product = findById(id);
        delete(product);
    }

    @Override
    public List<Product> findAll() {
        return productDAO.getAll();
    }

    @Override
    public List<Product> findAll(List<Long> ids) {
        return null;
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
        try {
            productDAO.get(product.getId());
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }
}
