package com.rafaelandrade.backend.services.exceptions;

public class CountryNotSupportedException extends Exception {
    public CountryNotSupportedException(String msg) {
        super(msg);
    }
}
