package com.rafaelandrade.backend.services.validation;

import com.rafaelandrade.backend.services.exceptions.CountryNotSupportedException;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PostalCodeValidatorManager {

    private static final Logger logger = LoggerFactory.getLogger(PostalCodeValidatorManager.class);
    private final Map<String, IPostalCodeValidator> validators;

    @Autowired
    public PostalCodeValidatorManager(BrazilPostalCodeValidator brazilPostalCodeValidator) {
        this.validators = Map.of(
                "Brasil", brazilPostalCodeValidator
        );
    }

    public boolean executePostalCodeValidator(String country, String postalCode) throws PostalCodeNotFoundException, CountryNotSupportedException {
        logger.info("Executing postal code validator for country: {} and postal code: {}", country, postalCode);

        IPostalCodeValidator validator = validators.get(country);

        if (validator == null) {
            logger.warn("Postal code validation is not supported for country: {}", country);
            throw new CountryNotSupportedException("Postal code validation is not supported for country: " + country);
        }

        try {
            boolean isValid = validator.isValidPostalCode(postalCode);
            logger.info("Postal code validation result for country {} and postal code {}: {}", country, postalCode, isValid);
            return isValid;
        } catch (PostalCodeNotFoundException e) {
            logger.error("Postal code validation failed for country {} and postal code {}: {}", country, postalCode, e.getMessage());
            throw e;
        }
    }
}