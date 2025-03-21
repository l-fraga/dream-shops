package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.model.Product;

import java.util.List;


public interface IProductService {
        // Defining the Product Service Interface
        Product getProductById(Long id);
    
        Product saveProduct(Product product);
    
        void deleteProductById(Long id);

        void updateProduct(Product product, Long id);

        List<Product> getAllProducts();

        List<Product> getProductsByCategory(String category);

        List<Product> getProductsByBrand(String brand);

        List<Product> getProductsByCategoryAndBrand(String category, String brand);

        List<Product> getProductsByName(String name);

        List<Product> getProductsByBrandAndName(String brand, String name);

        Long countProductsByBrandAndName(String brand, String name);
}
