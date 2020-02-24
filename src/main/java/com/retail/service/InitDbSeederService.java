package com.retail.service;

import com.retail.entity.Price;
import com.retail.entity.Product;
import com.retail.repository.ProductRepository;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InitDbSeederService {

  @Autowired
  private ProductRepository productRepository;

  /**
   * Default constructor.
   */
  public InitDbSeederService() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Populate data after initialize the application.
   */
  @PostConstruct
  public void init() {
    addProduct();
  }

  /**
   * Add dummy products.
   */
  private void addProduct() {
    if (productRepository != null) {
      Product product1 = Product.builder().productId("13860428")
          .currentPrice(Price.builder()
              .currency(Currency.getInstance("USD"))
              .value(50d)
              .build())
          .title("")
          .build();

      Product product2 = Product.builder().productId("16483589")
          .currentPrice(Price.builder()
              .currency(Currency.getInstance("USD"))
              .value(100.50d)
              .build())
          .title("")
          .build();

      Product product3 = Product.builder().productId("16696652")
          .currentPrice(Price.builder()
              .currency(Currency.getInstance("USD"))
              .value(55d)
              .build())
          .title("")
          .build();

      Product product4 = Product.builder().productId("15643793")
          .currentPrice(Price.builder()
              .currency(Currency.getInstance("USD"))
              .value(105.50d)
              .build())
          .title("")
          .build();
      // delete previous data
      this.productRepository.deleteAll();

      // Add product List in db.
      List<Product> products = Arrays.asList(product1, product2, product3, product4);
      this.productRepository.saveAll(products);
    }
  }
}
