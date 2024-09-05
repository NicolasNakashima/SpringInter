package org.example.apikhiata.repository;

import org.example.apikhiata.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
