package com.rafaelandrade.backend.integration.viacep;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "ViaCep")
public interface AddressFeign {
    @GetMapping("{cep}/json")
    AddressResponse findAddressByCep(@PathVariable("cep") String cep);
}
