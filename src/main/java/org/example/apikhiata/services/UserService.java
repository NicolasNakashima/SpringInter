package org.example.apikhiata.services;

import jakarta.transaction.Transactional;
import org.example.apikhiata.models.User;
import org.example.apikhiata.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //buscar cliente por nome
    public List<User> findUserByName(String name) {
        return userRepository.findByNameLikeIgnoreCase(name);
    }

    //buscar cliente por id
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id " + id));
    }

    //listar todos os clientes
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    @Transactional
    public User deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id " + id));
        userRepository.delete(user);
        return user;
    }
}
