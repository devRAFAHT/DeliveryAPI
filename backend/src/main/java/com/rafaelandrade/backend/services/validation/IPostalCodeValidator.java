package com.rafaelandrade.backend.services.validation;

import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;

public interface IPostalCodeValidator {
    boolean isValidPostalCode(String postalCode) throws PostalCodeNotFoundException;
}
