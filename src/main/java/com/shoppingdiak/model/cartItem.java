/**
 * 
 */
package com.shoppingdiak.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author HP
 *
 */
@Entity
@Table(name = "cartItem")
public class cartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
    Long id;
	

	@Column(name = "productnum", nullable = false, updatable = false)
    Long productnum;
	
	@Column(name = "productname", nullable = false, updatable = false)
    String productname;
	
	@Column(name = "productimage", nullable = false, updatable = false)
    String productimage;
	
	@Column(name = "quantite", nullable = false, updatable = false)
    int quantite;
	
	@Column(name = "productprice", nullable = false, updatable = false)
    double productprice;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the productnum
	 */
	public Long getProductnum() {
		return productnum;
	}

	/**
	 * @param productnum the productnum to set
	 */
	public void setProductnum(Long productnum) {
		this.productnum = productnum;
	}

	/**
	 * @return the productimage
	 */
	public String getProductimage() {
		return productimage;
	}

	/**
	 * @param productimage the productimage to set
	 */
	public void setProductimage(String productimage) {
		this.productimage = productimage;
	}

	/**
	 * @return the quantite
	 */
	public int getQuantite() {
		return quantite;
	}

	/**
	 * @param quantite the quantite to set
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	/**
	 * @return the productprice
	 */
	public double getProductprice() {
		return productprice;
	}

	/**
	 * @param productprice the productprice to set
	 */
	public void setProductprice(double productprice) {
		this.productprice = productprice;
	}

	

	/**
	 * @return the productname
	 */
	public String getProductname() {
		return productname;
	}

	/**
	 * @param productname the productname to set
	 */
	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * 
	 */
	public cartItem() {
		super();
	}
	

}
