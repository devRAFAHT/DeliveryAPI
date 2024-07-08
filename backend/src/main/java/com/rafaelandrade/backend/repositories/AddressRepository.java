package com.rafaelandrade.backend.repositories;

import com.rafaelandrade.backend.entities.Address;
import com.rafaelandrade.backend.entities.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
