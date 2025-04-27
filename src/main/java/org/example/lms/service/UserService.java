package org.example.lms.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.lms.entity.User;
import org.example.lms.entity.dto.RegisterRequest;
import org.example.lms.exception.EmailAlreadyTakenException;
import org.example.lms.exception.MobileAlreadyTakenException;
import org.example.lms.exception.PasswordMismatchException;
import org.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
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
        return userRepository.findByUsername(username);
    }
}
