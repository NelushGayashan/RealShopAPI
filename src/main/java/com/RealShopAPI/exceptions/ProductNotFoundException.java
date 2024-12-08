package com.RealShopAPI.exceptions;

public class ProductNotFoundException extends RuntimeException {

    // Constructor that accepts a message
    public ProductNotFoundException(String message) {
        super(message);
    }
}
