package org.dmdev.jwt.services;

import lombok.RequiredArgsConstructor;
import org.dmdev.jwt.entities.User;
import org.dmdev.jwt.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if ( repository.existByUsername(user.getUsername())) {
            throw new RuntimeException("Username is taken");
        }
        if(repository.existByEmail(user.getEmail())) {
            throw new RuntimeException("Email is taken");
        }
        return save(user);
    }

    public User getByUsername(String username){
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
