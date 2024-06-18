package com.rafaelandrade.backend.services.validation;

import com.rafaelandrade.backend.integration.viacep.AddressFeign;
import com.rafaelandrade.backend.integration.viacep.AddressResponse;
import com.rafaelandrade.backend.services.exceptions.PostalCodeNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrazilPostalCodeValidator implements IPostalCodeValidator{

    private AddressFeign addressFeign;

    @Autowired
    public BrazilPostalCodeValidator(AddressFeign addressFeign) {
        this.addressFeign = addressFeign;
    }

    @Override
    public boolean isValidPostalCode(String postalCode) throws PostalCodeNotFoundException {
        try {
            AddressResponse addressResponse = addressFeign.findAddressByCep(postalCode);
            if (addressResponse.getErro() == null) {
                return true;
            } else {
                throw new PostalCodeNotFoundException("CEP not found");
            }
        } catch (FeignException.BadRequest e) {
            throw new PostalCodeNotFoundException("Invalid CEP format");
        } catch (FeignException.InternalServerError e) {
            throw new PostalCodeNotFoundException("Internal error on ViaCep server");
        } catch (FeignException.Forbidden e) {
            throw new PostalCodeNotFoundException("Forbidden: Not authorized to access the ViaCep server");
        } catch (FeignException.TooManyRequests e) {
            throw new PostalCodeNotFoundException("Many requests on the ViaCep server");
        } catch (FeignException e) {
            throw new PostalCodeNotFoundException("Error while ViaCep server: " + e.getMessage());
        }
    }

}