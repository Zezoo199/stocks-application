package com.payconiq.stocks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockWithSameNameAlreadyExistException extends Exception {
  public StockWithSameNameAlreadyExistException(String msg) {
    super(msg);
  }
}
