package org.example.apikhiata.repository;

import org.example.apikhiata.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByNameLikeIgnoreCase(String nome);
    Optional<User> findByEmailLikeIgnoreCase(String email);

    Optional<User> findByCpf(String cpf);

}
