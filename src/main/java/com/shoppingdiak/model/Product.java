package com.shoppingdiak.model;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "product")
//@NamedQueries(
//		{ @NamedQuery(name = "findProductByOurpriceLessThanEqual", query = "FROM product WHERE our_price <= ?1") }
//		
//		)

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title", nullable = true, updatable = true)
    private String title;
    @Column(name = "publisher", nullable = true, updatable = true)
    private String publisher;
    @Column(name = "category", nullable = true, updatable = true)
    private String category;
    @Column(name = "publishedDate", nullable = true, updatable = true)
    private String publishedDate;
    @Column(name = "shippingWeight", nullable = false, updatable = true)
    private double shippingWeight;
    @Column(name = "listPrice", nullable = false, updatable = true)
    private double listPrice;
    @Column(name = "price", nullable = false, updatable = true)
    private double price;
    @Column(name = "active", nullable = false, updatable = true)
    private boolean active = true;
    @Column(name = "description", nullable = true, updatable = true)
    private String description;
    @Column(name = "inStockNumber", nullable = false, updatable = true)
    private int inStockNumber;
    @Column(name = "product_image", nullable = false, updatable = true)
    private String product_image ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    public Product() {
    }

    public Product(String title, String publisher, String category, String publishedDate, double shippingWeight, double listPrice, double price, boolean active, String description, int inStockNumber, String product_image ) {
        this.title = title;
        this.publisher = publisher;
        this.category = category;
        this.publishedDate = publishedDate;
        this.shippingWeight = shippingWeight;
        this.listPrice = listPrice;
        this.price = price;
        this.active = active;
        this.description = description;
        this.inStockNumber = inStockNumber;
        this.product_image  = product_image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public double getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInStockNumber() {
        return inStockNumber;
    }

    public void setInStockNumber(int inStockNumber) {
        this.inStockNumber = inStockNumber;
    }

   
    /**
	 * @return the product_image
	 */
	public String getProduct_image() {
		return product_image;
	}

	/**
	 * @param product_image the product_image to set
	 */
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}

	@Override
    public int hashCode() {
        return id.hashCode();
    }
}
