package org.example.apikhiata.services;

import jakarta.transaction.Transactional;
import org.example.apikhiata.models.User;
import org.example.apikhiata.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //buscar usuário por nome
    public List<User> findUserByName(String name) {
        return userRepository.findByNameLikeIgnoreCase(name);
    }

    //buscar usuário por id
    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id " + id));
    }

    //busca um usuário pelo email
    public User findUserByEmail(String email) {
        return userRepository.findByEmailLikeIgnoreCase(email).orElseThrow(() -> new RuntimeException("Cliente não encontrado com o email " + email));
    }

    public User findUserByCPF(String cpf) {
        return userRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("Cliente não encontrado com o email " + cpf));
    }

    //listar todos os usuários
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //salva usuário
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    //deleta um usuário
    @Transactional
    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    //deleta usuário por id
    @Transactional
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id " + id));
        userRepository.delete(user);
    }

    //atualizar usuário por id
    @Transactional
    public User updateUser(int id,User user) {
        User existingUser = findUserById(id);
        existingUser.setName(user.getName());
        existingUser.setCpf(user.getCpf());
        existingUser.setGenderId(user.getGenderId());
        existingUser.setAge(user.getAge());
        existingUser.setIsDressmaker(user.getIsDressmaker());
        existingUser.setIsPremium(user.getIsPremium());
        existingUser.setPhone(user.getPhone());
        existingUser.setImageURL(user.getImageURL());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setProfilePictureUrl(user.getProfilePictureUrl());
        return userRepository.save(existingUser);
    }
}
