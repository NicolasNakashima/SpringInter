package org.example.apikhiata.repository;

import org.example.apikhiata.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameLikeIgnoreCase(String nome);
}
