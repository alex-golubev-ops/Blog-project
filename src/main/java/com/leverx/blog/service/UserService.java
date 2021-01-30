package com.leverx.blog.service;

import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.User;
import com.leverx.blog.repository.CodeRepository;
import com.leverx.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    public UserService(UserRepository userRepository, CodeRepository codeRepository,
                       MailService mailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findByEmail(email);

        return userFromDb.orElseThrow(() -> new UsernameNotFoundException("User is not found " + email));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean save(User user) {
        Optional<User> userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb.isPresent()) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setAtDate(LocalDate.now());
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        boolean result = false;
        String emailFromRedis = codeRepository.findByCode(code);
        if (emailFromRedis != null) {
            User user = findByEmail(emailFromRedis).get();
            user.setActive(true);
            userRepository.save(user);
            codeRepository.delete(code);
            result = true;
        }
        return result;
    }

    public boolean forgotPassword(String email) {
        Optional<User> userFromBd = findByEmail(email);
        if (userFromBd.isEmpty()) {
            return false;
        }
        String activationCode = Integer.toString(random.nextInt(10001));
        User user = userFromBd.get();
        codeRepository.save(activationCode, user.getEmail());
        String message = String.format("Hello, %s!\n" +
                        "Your code:\n" +
                        activationCode,
                user.getFirstname() + " " + user.getLastname());
        mailService.send(user.getEmail(), "Forgot Password Code: ", message);
        return true;
    }

    public boolean checkCode(String code, String email) {
        boolean result = false;
        String emailFromRedis = codeRepository.findByCode(code);
        if (emailFromRedis == null) {
            return result;
        }
        if (emailFromRedis.equals(email)) {
            result = true;
        }
        return result;
    }

    public boolean resetPassword(String code, String password) {
        boolean result = false;
        String emailFromRedis = codeRepository.findByCode(code);
        Optional<User> byEmail = findByEmail(emailFromRedis);
        if (byEmail.isEmpty()) {
            return result;
        }
        User user = byEmail.get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        codeRepository.delete(code);
        return result;
    }
}
