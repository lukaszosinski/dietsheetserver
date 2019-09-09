package com.dietsheet_server.service;


import com.dietsheet_server.DAO.ProductDAO;
import com.dietsheet_server.DAO.QueryParams;
import com.dietsheet_server.model.diet.DietEntityCascadeUpdater;
import com.dietsheet_server.model.diet.Product;
import com.dietsheet_server.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service("productService")
@Transactional
public class ProductServiceImpl implements Service<Product>{

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private DietEntityCascadeUpdater dietEntityCascadeUpdater;


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
        productToUpdate.setGranularity(productUpdateData.getGranularity());
        productToUpdate.setDescription(productUpdateData.getDescription());
        productToUpdate.updatePrices(productUpdateData.getPrices());
        productToUpdate.updateSummary(productUpdateData.getSummary());
        productDAO.update(productToUpdate);
        dietEntityCascadeUpdater.cascadeUpdateParents(productToUpdate);

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
    public List<Product> findAll(User user, Map<String, String> params) {
        QueryParams queryParams = new QueryParams(params);
        return productDAO.getAllByUser(user, queryParams);
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
