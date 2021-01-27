package com.leverx.blog.service;

import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.repository.CodeRepository;
import com.leverx.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final MailService mailService;

    public UserService(UserRepository userRepository, CodeRepository codeRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByEmail(email);

        return userFromDb.orElseThrow(() -> new UsernameNotFoundException("User is not found " + email));
    }

    public User findByEmail(String email) {
        Optional<User> userFromDb = userRepository.findByEmail(email);
        return  userFromDb.get();
    }


    public boolean save(User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setAtDate(LocalDate.now());
        user.setActive(false);
        String activationCode = UUID.randomUUID().toString();
        codeRepository.save(activationCode, user.getEmail());
        String message = String.format("Hello, %s!\n" +
                        "Welcome to my blog!\n" +
                        "Please visit next link: %s",
                user.getFirstname() + " " + user.getLastname(),
                "localhost:8081/activate/" + activationCode);
        mailService.send(user.getEmail(), "Activation Code: ", message);
        userRepository.save(user);
        return true;
    }

    public boolean activate(String code) {
        boolean result=false;
        String emailFromRedis = codeRepository.findByCode(code);
        if(emailFromRedis!=null){
            User user = findByEmail(emailFromRedis);
            user.setActive(true);
            userRepository.save(user);
            codeRepository.delete(code);
            result=true;
        }
        return result;

    }
}
