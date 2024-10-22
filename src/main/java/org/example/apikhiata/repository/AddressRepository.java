package org.example.apikhiata.repository;

import org.example.apikhiata.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Procedure(procedureName = "DELETE_ADRESS")
    void deleteAddressByProcedure(@Param("adress_id") int adressId);
}
