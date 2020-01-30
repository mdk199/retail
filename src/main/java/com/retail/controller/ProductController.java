package com.retail.controller;

import com.retail.entity.Product;
import com.retail.exception.CustomResponse;
import com.retail.exception.ProductMisMatchException;
import com.retail.exception.ProductNotFoundException;
import com.retail.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**

 */
@RequestMapping(value="/products")
@RestController
public class ProductController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductService productService;
	
	/**
	 * @return
	 * This method can be used to fetch all the products. 
	 * But here I just want to show that any user can see this page. (Means no security here.)
	 * 
	 */
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String index() {
		return "No security on me. I can show all the products when implemented.";
	}

	/**
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProductInfo(@PathVariable("id") String productId) {
		logger.info("Inside getproductInfo  " + productId);

		Product product = null;
		try {
			product = productService.getProductById(productId);
		} catch (Exception e) {
			logger.debug("Product Not Found Exception  " + e);
			throw new ProductNotFoundException();
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	/**
	 * @param productId
	 * @return
	 * 
	 * Add method level security. Only admin can change the product price.
	 *  
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CustomResponse> modifyPrice(@RequestBody Product product,
			@PathVariable("id") String productId) {
		if (!product.productId.equalsIgnoreCase(productId)) {
			throw new ProductMisMatchException();
		}
		try {
			productService.updateProductById(product);
		} catch (Exception e) {
			logger.debug("Product Not Found Exception while update " + e);
			throw new ProductNotFoundException();
		}

		return new ResponseEntity<CustomResponse>(
				new CustomResponse(HttpStatus.OK.value(), "Product price has been updated"), HttpStatus.OK);
	}
}
