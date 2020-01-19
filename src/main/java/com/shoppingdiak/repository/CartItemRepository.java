/**
 * 
 */
package com.shoppingdiak.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingdiak.model.cartItem;

/**
 * @author HP
 *
 */
public interface CartItemRepository extends JpaRepository<cartItem, Long> {
	 	
}
