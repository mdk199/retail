package com.retail.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.entity.Product;
import com.retail.external.client.ProductInfoClient;
import com.retail.repository.ProductRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ProductService {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ProductInfoClient productInfoClient;

  @Autowired
  private ProductRepository productRepository;

  private ExecutorService executorService;

  public ProductService() {
    executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }

  /**
   * @param productId
   * @return
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  public Product getProductById(String productId)
      throws Exception {
    // From application DB
    final CompletableFuture<Product> dbProductCall = CompletableFuture
        .supplyAsync(() -> productRepository.getProductByproductId(productId));
    // From ProductInfoClient
    final CompletableFuture<String> titleCall = CompletableFuture.supplyAsync(() -> {
      try {
        return this.getTitleForProduct(productId);
      } catch (Exception e) {
        logger.error("Error while fetching title for: {}", productId);
        throw new RuntimeException();
      }
    });

    final CompletableFuture<Product> productCompletableFuture = dbProductCall
        .thenCombineAsync(titleCall, (product, title) -> {
          product.setTitle(title);
          return product;
        });

    return productCompletableFuture.get();
  }

  /**
   * @param productId
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonParseException
   * @throws Exception            get the title from
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private String getTitleForProduct(String productId)
      throws Exception {
    Map<String, Map> infoMap = getProductInfoFromProductInfoService(productId);

    Map<String, Map> productMap = infoMap.get("product");
    Map<String, Map> itemMap = productMap.get("item");
    Map<String, String> prodDescrMap = itemMap.get(("product_description"));

    return prodDescrMap.get("title");
  }

  /**
   * @param productId
   * @return
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException          Getting remote data using Feign product service.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private Map<String, Map> getProductInfoFromProductInfoService(String productId)
      throws Exception {
    ObjectMapper infoMapper = new ObjectMapper();
    System.out.println(productInfoClient);
    ResponseEntity<String> response = productInfoClient.getProductInfoById(productId);
    System.out.println(response.getStatusCode().value());
    Map<String, Map> infoMap = infoMapper.readValue(response.getBody(), Map.class);

    return infoMap;
  }

  /**
   * @param product
   */
  public void updateProductById(Product product) {
    productRepository.save(product);
  }

}
