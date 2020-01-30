package com.retail.repository;

import com.retail.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**

 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
	/**
	 * @param productId
	 * @return
	 */
	public Product getProductByproductId(String productId);
}
