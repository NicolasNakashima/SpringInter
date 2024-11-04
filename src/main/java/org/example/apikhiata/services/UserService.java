package org.example.apikhiata.services;

import jakarta.transaction.Transactional;
import org.example.apikhiata.models.User;
import org.example.apikhiata.models.UserPreference;
import org.example.apikhiata.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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

    //listar todos os usuários esperando aprovação
    public List<User> findUsersAwaitingPremiumApproval() {
        return userRepository.findUsersByPremiumStatus(2);
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
                    existingUser.setPremiumStatus((int) valor);
                    break;
                case "phones":
                    existingUser.setPhones((String) valor);
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
                case "avaliation":
                    existingUser.setAvaliation((double) valor);
                    break;
                case "isAdmin":
                    existingUser.setIsAdmin((boolean) valor);
                    break;
                case "userPreferences":
                    Set<UserPreference> preferencias = new HashSet<>();
                    if (valor instanceof Set<?>) {
                        preferencias.addAll((Set<UserPreference>) valor);
                    } else if (valor instanceof List<?>) {
                        List<UserPreference> listaPreferencias = (List<UserPreference>) valor;
                        preferencias.addAll(listaPreferencias);
                    } else {
                        throw new IllegalArgumentException("As preferências do usuário devem ser um conjunto ou uma lista.");
                    }

                    for (UserPreference preferencia : preferencias) {
                        preferencia.setUser(existingUser);
                        existingUser.getUserPreferences().add(preferencia);
                    }
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

    public User updateUserPreferences(int userId, List<UserPreference> newPreferences) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.getUserPreferences().clear();  //limpa as preferencias salvas para depois incluir novas
        newPreferences.forEach(pref -> pref.setUser(user));
        user.getUserPreferences().addAll(newPreferences);
        return userRepository.save(user);
    }



}
