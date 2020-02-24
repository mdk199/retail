package com.retail.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.retail.entity.Price;
import com.retail.entity.Product;
import com.retail.external.client.ProductInfoClient;
import com.retail.external.client.ProductInfoClientMock;
import com.retail.repository.ProductRepository;
import java.io.IOException;
import java.util.Currency;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

  @InjectMocks
  ProductService productService;

  @Mock // -- Spring Boot
      ProductRepository productrepositoryMock;

  @Mock
  private ProductInfoClient productInfoClient;

  /**
   * Setup for Mockito before any test run.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonParseException   Positive test.
   */
  @Test
  public void getProductByIdTest() throws Exception {
    Product mockProduct = Product.builder().productId("13860428")
        .currentPrice(Price.builder()
            .currency(Currency.getInstance("USD"))
            .value(50d)
            .build())
        .build();
    Mockito.when(productrepositoryMock.getProductByproductId(Mockito.anyString()))
        .thenReturn(mockProduct);

    Mockito.when(productInfoClient.getProductInfoById(Mockito.anyString()))
        .thenReturn(new ProductInfoClientMock().getProductInfoById("13860428"));

    // Actual Result
    Product actualProduct = productService.getProductById("13860428");

    // Expected Result
    Product expectedProduct = Product.builder().productId("13860428")
        .currentPrice(Price.builder()
            .currency(Currency.getInstance("USD"))
            .value(50d)
            .build())
        .build();
    assertEquals(expectedProduct.getProductId(), actualProduct.getProductId());
  }

  /**
   * @throws Exception Check for null pointer exception when wrong product id passed to Remote
   *                   service.
   */
  @Test
  public void getProductInfoTest_wrongProductId() throws Exception {
    Assertions.assertThrows(ExecutionException.class, () -> {
      Product mockProduct = Product.builder().productId("13860428")
          .currentPrice(Price.builder()
              .currency(Currency.getInstance("USD"))
              .value(50d)
              .build())
          .build();
      Mockito.when(productrepositoryMock.getProductByproductId(Mockito.anyString()))
          .thenReturn(mockProduct);
      productService.getProductById("12345678");
    });
  }
}
