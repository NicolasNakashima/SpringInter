package org.example.apikhiata.repository;

import org.example.apikhiata.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
