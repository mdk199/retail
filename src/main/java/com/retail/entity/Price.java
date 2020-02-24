package com.retail.entity;

import java.io.Serializable;
import java.util.Currency;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Price implements Serializable {

  @Min(0)
  private double value;

  private Currency currency;
}

