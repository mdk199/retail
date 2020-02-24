package com.retail.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.retail.RetailApplication;
import com.retail.entity.Price;
import com.retail.exception.ProductNotFoundException;
import com.retail.service.InitDbSeederService;
import java.io.File;
import java.text.MessageFormat;
import java.util.Currency;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RetailApplication.class)
@WebAppConfiguration
class ProductControllerTest {

  protected MockMvc mvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private InitDbSeederService initDbSeederService;

  private ObjectMapper objectMapper;


  @BeforeEach
  void setUp() {
    initDbSeederService.init();
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    objectMapper = new ObjectMapper();
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  public void whenGetProductInfoForIncorrectIdThenThrowProductNotFoundException() throws Exception {
    String uri = getProductURI("DUMMY_PRODUCT_ID");
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    Assertions
        .assertEquals(ProductNotFoundException.class, mvcResult.getResolvedException().getClass());
  }

  @Test
  public void whenGetProductInfoForCorrectIdThenWorksAsExpected() throws Exception {
    final String uri = getProductURI("13860428");
    final JsonNode EXPECTED_RESULT = objectMapper
        .readTree(new File("src/test/resources/fixtures/expected-product.json"));
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    JSONAssert
        .assertEquals(EXPECTED_RESULT.toString(), mvcResult.getResponse().getContentAsString(),
            Boolean.TRUE);
  }

  @Test
  public void testModifyPrice() throws Exception {
    final String uri = getProductURI("13860428");
    final JsonNode EXPECTED_RESULT = objectMapper
        .readTree(new File("src/test/resources/fixtures/expected-product-updated.json"));
    MvcResult mvcResult = mvc
        .perform(
            MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(
                objectMapper.writeValueAsString(
                    Price.builder().currency(Currency.getInstance(Locale.US)).value(90).build())))
        .andReturn();
    JSONAssert
        .assertEquals(EXPECTED_RESULT.toString(), mvcResult.getResponse().getContentAsString(),
            Boolean.TRUE);
  }

  private String getProductURI(String productId) {
    return MessageFormat.format(Joiner.on("/").join("/products", "{0}"), productId);
  }
}