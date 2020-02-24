package com.retail.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.retail.entity.Price;
import com.retail.entity.Product;
import com.retail.exception.CustomResponse;
import com.retail.exception.ProductNotFoundException;
import com.retail.service.ProductService;
import java.io.IOException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RequestMapping(value = "/products")
@RestController
public class ProductController {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ProductService productService;

  /**
   * @return This method can be used to fetch all the products. But here I just want to show that
   * any user can see this page. (Means no security here.)
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index() {
    return "No security on me. I can show all the products when implemented.";
  }

  /**
   * @param productId
   * @return
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
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
   * @return Add method level security. Only admin can change the product price.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> modifyPrice(@RequestBody @Valid Price price,
      @PathVariable("id") String productId) throws Exception {
    try {
      productService.updateProductById(Product.builder()
          .currentPrice(price)
          .productId(productId)
          .build());
    } catch (Exception e) {
      logger.debug("Product Not Found Exception while update " + e);
      throw new ProductNotFoundException();
    }

    return new ResponseEntity<Product>(productService.getProductById(productId), HttpStatus.OK);
  }
}
