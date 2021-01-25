package com.leverx.blog.service;

import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByEmail("saidak@gmail.com");

        return userFromDb.orElseThrow(() -> new UsernameNotFoundException("User is not found " + email));
    }

    public User findByEmail(String email) {
        return findByEmail(email);
    }


    public boolean save(User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setAtDate(LocalDate.now());
        user.setActive(true);
        userRepository.save(user);
        return true;
    }
}
