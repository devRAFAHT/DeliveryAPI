package com.rafaelandrade.backend.services.validation;

import com.rafaelandrade.backend.integration.viacep.AddressFeign;
import com.rafaelandrade.backend.integration.viacep.AddressResponse;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrazilPostalCodeValidator implements IPostalCodeValidator {

    private static final Logger logger = LoggerFactory.getLogger(BrazilPostalCodeValidator.class);
    private final AddressFeign addressFeign;

    @Autowired
    public BrazilPostalCodeValidator(AddressFeign addressFeign) {
        this.addressFeign = addressFeign;
    }

    @Override
    public boolean isValidPostalCode(String postalCode) throws PostalCodeNotFoundException {
        logger.info("Validating postal code: {}", postalCode);
        try {
            AddressResponse addressResponse = addressFeign.findAddressByCep(postalCode);
            if (addressResponse.getErro() == null) {
                logger.info("Postal code {} is valid", postalCode);
                return true;
            } else {
                logger.warn("Postal code {} is not found", postalCode);
                throw new PostalCodeNotFoundException("CEP not found");
            }
        } catch (FeignException.BadRequest e) {
            logger.error("Invalid CEP format: {}", postalCode, e);
            throw new PostalCodeNotFoundException("Invalid CEP format");
        } catch (FeignException.InternalServerError e) {
            logger.error("Internal error on ViaCep server for postal code: {}", postalCode, e);
            throw new PostalCodeNotFoundException("Internal error on ViaCep server");
        } catch (FeignException.Forbidden e) {
            logger.error("Forbidden access for postal code: {}", postalCode, e);
            throw new PostalCodeNotFoundException("Forbidden: Not authorized to access the ViaCep server");
        } catch (FeignException.TooManyRequests e) {
            logger.error("Too many requests for postal code: {}", postalCode, e);
            throw new PostalCodeNotFoundException("Many requests on the ViaCep server");
        } catch (FeignException e) {
            logger.error("Error while accessing ViaCep server for postal code: {}", postalCode, e);
            throw new PostalCodeNotFoundException("Error while ViaCep server: " + e.getMessage());
        }
    }
}