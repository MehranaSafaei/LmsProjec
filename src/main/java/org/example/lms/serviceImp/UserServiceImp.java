package org.example.lms.serviceImp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lms.entity.Role;
import org.example.lms.entity.User;
import org.example.lms.entity.dto.RegisterRequest;
import org.example.lms.exception.EmailAlreadyTakenException;
import org.example.lms.exception.MobileAlreadyTakenException;
import org.example.lms.exception.PasswordMismatchException;
import org.example.lms.repository.RoleRepository;
import org.example.lms.repository.UserRepository;
import org.example.lms.serviceImp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserDetailsService, UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    public void registerUser(RegisterRequest request) throws EmailAlreadyTakenException, MobileAlreadyTakenException, MessagingException, PasswordMismatchException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyTakenException();
        }
        if (userRepository.existsByMobile(request.getMobile())){
            throw new MobileAlreadyTakenException();
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        sendConfirmationEmail(user);
    }

    private void sendConfirmationEmail(User user) throws MessagingException {
        String subject = "Registration Confirmation";
        String text = "Dear " + user.getEmail() + ",\n\n" +
                "Thank you for registering. We are excited to have you with us!\n\n" +
                "Best regards,\n" +
                "The Team";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(text);

        mailSender.send(mimeMessage);
    }


    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public User saveUser(User user) {
        log.info("saving new user {} to the database", user.getName());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to the database", role.getName() );
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to user {}", username, roleName );
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }
}
