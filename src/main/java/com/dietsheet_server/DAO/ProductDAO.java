package com.dietsheet_server.DAO;


import com.dietsheet_server.model.diet.Product;
import org.springframework.stereotype.Component;

@Component("productDAO")
public class ProductDAO extends AbstractDAO<Product> {
    public ProductDAO() {
        setClazz(Product.class);
    }
}
