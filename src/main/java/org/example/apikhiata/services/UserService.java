package org.example.apikhiata.services;

import jakarta.transaction.Transactional;
import org.example.apikhiata.models.User;
import org.example.apikhiata.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id " + id));
    }

    //busca um usuário pelo email
    public User findUserByEmail(String email) {
        return userRepository.findByEmailLikeIgnoreCase(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email " + email));
    }

    public User findUserByCPF(String cpf) {
        return userRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email " + cpf));
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


    //deleta usuário por id
    @Transactional
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id " + id));
        userRepository.deleteUserByProcedure(id);
    }


    public void deleteUserByEmail(  String email ) {
        User user = findUserByEmail(email);
        userRepository.deleteUserByProcedure(user.getId());
    }

    private void updatePartialUserFields(User existingUser, Map<String, Object> atualizacoes) {
        atualizacoes.forEach((campo, valor) -> {
            if (campo.equals("email") || campo.equals("cpf")) {
                throw new IllegalArgumentException("O campo " + campo + " não pode ser alterado.");
            }
            switch (campo) {
                case "name":
                    existingUser.setName((String) valor);
                    break;
                case "genderId":
                    existingUser.setGenderId((Integer) valor);
                    break;
                case "age":
                    existingUser.setAge((Integer) valor);
                    break;
                case "isDressmaker":
                    existingUser.setIsDressmaker((Boolean) valor);
                    break;
                case "premiumStatus":
                    existingUser.setIsPremium((int) valor);
                    break;
                case "phones":
                    existingUser.setPhone((String) valor);
                    break;
                case "imageURL":
                    existingUser.setImageURL((String) valor);
                    break;
                case "password":
                    existingUser.setPassword((String) valor);
                    break;
                case "profilePictureUrl":
                    existingUser.setProfilePictureUrl((String) valor);
                    break;
                default:
                    throw new IllegalArgumentException("Campo está errado: " + campo);
            }
        });

    }

    @Transactional
    public User updatePartialUserWithId(int id, Map<String, Object>atualizacoes) {
        User existingUser = findUserById(id);
        updatePartialUserFields(existingUser, atualizacoes);
        return userRepository.save(existingUser);
    }

    @Transactional
    public User updatePartialUserWithEmail(String email, Map<String, Object>atualizacoes) {
        User existingUser = findUserByEmail(email);
        updatePartialUserFields(existingUser, atualizacoes);
        return userRepository.save(existingUser);
    }
}
