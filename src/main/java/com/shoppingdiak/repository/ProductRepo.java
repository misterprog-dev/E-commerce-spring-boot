package com.shoppingdiak.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shoppingdiak.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findProductById(Long id);
    List<Product> findAll();
    List<Product> findProductByCategory(String category);
    List<Product> findProductByPriceLessThanEqual(double our_price);
    List<Product> findProductByTitleLike(String title);
    List<Product> findByTitleContaining(String title);
    List<Product> findProductByCategoryLike(String Category);
    List<Product> findProductByCategoryContaining(String Category);
    
//    
//    @Query("update product set inStockNumber = inStockNumber - ?1 where id = ?2")
//    void updateinStockNumber(int nombre, Long id);
    
}
