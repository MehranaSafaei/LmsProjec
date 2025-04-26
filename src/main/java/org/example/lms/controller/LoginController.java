package org.example.lms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.lms.entity.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/auth/lms")
@AllArgsConstructor
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @PostMapping("/loginUser")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
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
