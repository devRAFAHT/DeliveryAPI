package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Address;
import com.rafaelandrade.backend.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
