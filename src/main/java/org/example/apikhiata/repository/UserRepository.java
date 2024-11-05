package org.example.apikhiata.repository;

import org.example.apikhiata.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByNameLikeIgnoreCase(String nome);

    Optional<User> findByNameLikeIgnoreCase(String nome);
    Optional<User> findByEmailLikeIgnoreCase(String email);

    Optional<User> findByCpf(String cpf);

    @Procedure(procedureName = "DELETE_USER")
    void deleteUserByProcedure(@Param("user_id") int userId);

    List<User> findUsersByPremiumStatus(int premiumStatus);

}
