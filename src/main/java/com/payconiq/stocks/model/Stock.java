package com.payconiq.stocks.model;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import org.javamoney.moneta.Money;

public class Stock {
  private long id;
  private String name;

  @ApiModelProperty(
      example = "{\n" + "      \"amount\": 10,\n" + "      \"currency\": \"EUR\"\n" + "    }")
  private Money currentPrice;

  private LocalDateTime lastUpdate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Money getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(Money currentPrice) {
    this.currentPrice = currentPrice;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
