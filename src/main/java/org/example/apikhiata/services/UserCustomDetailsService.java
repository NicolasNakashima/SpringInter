package org.example.apikhiata.services;

import org.example.apikhiata.models.User;
import org.example.apikhiata.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserCustomDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserCustomDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByNameLikeIgnoreCase(username).orElseThrow(()-> new UsernameNotFoundException("Usuário não existe"));
        if(user.getIsAdmin()){
            return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("Usuário não autorizado");
        }
    }
}

