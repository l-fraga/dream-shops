package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.model.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductService implements IProductService {

    @Override
    public Product getProductById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductById'");
    }

    @Override
    public Product saveProduct(Product product) {
        throw new UnsupportedOperationException("Unimplemented method 'saveProduct'");
    }

    @Override
    public void deleteProductById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteProductById'");
    }

    @Override
    public void updateProduct(Product product, Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public List<Product> getAllProducts() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByBrand'");
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategoryAndBrand'");
    }

    @Override
    public List<Product> getProductsByName(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByName'");
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByBrandAndName'");
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        throw new UnsupportedOperationException("Unimplemented method 'countProductsByBrandAndName'");
    }
    
}
          