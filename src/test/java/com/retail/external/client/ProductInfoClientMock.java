package com.retail.external.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductInfoClientMock implements ProductInfoClient {

  /*
   * (non-Javadoc)
   *
   * @see com.retail.remoteApiComm.feignClient.ProductInfoClient#
   * getProductInfoById(java.lang.String)
   */
  @Override
  public ResponseEntity<String> getProductInfoById(String productId) {
    String productInfo = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"The Big Lebowski (Blu-ray)\"}}}}";

    return new ResponseEntity<String>(productInfo, HttpStatus.OK);
  }

}
