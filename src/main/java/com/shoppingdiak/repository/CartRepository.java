/**
 * 
 */
package com.shoppingdiak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingdiak.model.Cart;

/**
 * @author HP
 *
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
	
}
