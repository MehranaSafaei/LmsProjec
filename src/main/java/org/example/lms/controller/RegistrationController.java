package org.example.lms.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.lms.entity.Role;
import org.example.lms.entity.User;
import org.example.lms.entity.dto.SignupRequest;
import org.example.lms.entity.dto.UserDto;
import org.example.lms.repository.RoleRepository;
import org.example.lms.repository.UserRepository;
import org.example.lms.service.RegistrationService;
import org.example.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping(path = "/api/auth/lms")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest, HttpServletRequest request){

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("Email already used!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        user.setRoles(Set.of(role, adminRole));
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
//    @GetMapping(path = "/register")
//    public String registrationPage(HttpSession session) {
//        if (session.getAttribute("user") != null) {
//            return "redirect:/dashboard";
//        }
//        return "registration";
//    }
//
//    @PostMapping()
//    public ResponseEntity<?> register(@ModelAttribute RegistrationRequest registrationRequest, Model model, HttpSession session) {
//        try {
//            String result = registrationService.register(registrationRequest);
//            return ResponseEntity.ok().body(result);
//        }catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }
//    public String home(){
//        return "index";
//    }
//
//    // handler method to handle login request
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//    // handler method to handle user registration form request
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model){
//        // create model object to store form data
//        UserDto user = new UserDto();
//        model.addAttribute("user", user);
//        return "register";
//    }
//
//    // handler method to handle user registration form submit request
//    @PostMapping("/register/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
//                               BindingResult result,
//                               Model model){
//        User existingUser = userService.findUserByEmail(userDto.getClass());
//
//        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if(result.hasErrors()){
//            model.addAttribute("user", userDto);
//            return "/register";
//        }
//
//        userService.saveUser(userDto);
//        return "redirect:/register?success";
//    }
//
//    // handler method to handle list of users
//    @GetMapping("/users")
//    public String users(Model model){
//        List<UserDto> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
}
