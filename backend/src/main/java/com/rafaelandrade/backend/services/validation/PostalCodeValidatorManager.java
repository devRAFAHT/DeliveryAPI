package com.rafaelandrade.backend.services.validation;

import com.rafaelandrade.backend.services.exceptions.CountryNotSupportedException;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PostalCodeValidatorManager {

    private final Map<String, IPostalCodeValidator> validators;

    @Autowired
    public PostalCodeValidatorManager(BrazilPostalCodeValidator brazilPostalCodeValidator) {
        this.validators = Map.of(
                "Brasil", brazilPostalCodeValidator
        );
    }

    public boolean executePostalCodeValidator(String country, String postalCode) throws PostalCodeNotFoundException, CountryNotSupportedException {
        IPostalCodeValidator validator = validators.get(country);

        if(validator == null){
            throw new CountryNotSupportedException("Postal code validation is not supported for country: " + country);
        }

        return validator.isValidPostalCode(postalCode);
    }
}
