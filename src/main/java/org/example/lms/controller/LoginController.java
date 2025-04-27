package org.example.lms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.lms.entity.User;
import org.example.lms.entity.dto.LoginRequest;
import org.example.lms.security.Jwt;
import org.example.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/auth/lms")
@AllArgsConstructor
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private Jwt jwtUtil;


//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            Authentication authenticationRequest =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            User user = (User) authenticationResponse.getPrincipal();
            String token = jwtUtil.generateToken(user);
            response.put("token", token);
//            response.put("message", "User signed-in successfully!");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("message", "Invalid credentials. Please try again.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (LockedException e) {
            response.put("message", "Your account is locked. Please contact support.");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (DisabledException e) {
            response.put("message", "Your account is disabled. Please contact support.");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            response.put("message", "An unexpected error occurred. Please try again later.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user-info")
    public String userInfoPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        return user.toString();
    }




//    @GetMapping
//    public String loginPage(HttpSession session) {
//
//        if (session.getAttribute("user") != null) {
//            return "redirect:/dashboard";
//        }
//        return "login";
//    }
//
//    @PostMapping
//    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
//        boolean isAuthenticated = registrationService.authenticate(username, password);
//
//        if (isAuthenticated) {
//            session.setAttribute("user", username);
//            model.addAttribute("username", username);
//            return "dashboard";
//        } else {
//            model.addAttribute("error", "Invalid username or password");
//            return "login";
//        }
//    }
}
