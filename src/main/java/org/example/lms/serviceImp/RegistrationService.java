package org.example.lms.serviceImp;

//@Service
//@AllArgsConstructor
//public class RegistrationService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RegistrationMapper registrationMapper;
//
//    public String register(LoginRequest registrationRequest, Model model) {
//        if (registrationRequest.getPassword() == null || registrationRequest.getPassword().length() < 8) {
//            throw new IllegalArgumentException("Password must be at least 8 characters long");
//        }
//
//        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("User with this email address already exists");
//        }
//        User user = registrationMapper.convertDtoToEntity(registrationRequest);
//        User savedUser = userRepository.save(user);
//        LoginRequest savedUserDto = registrationMapper.convertEntityToDto(savedUser);
//        model.addAttribute("user", savedUserDto);
//        return "Registration successful";
//    }
//
//
//    public boolean authenticate(String username, String password) {
//        User user = userRepository.findByUsername((username)).orElse(null);
//        return user != null && user.getPassword().equals(password);
//    }
//
//    public boolean userExists(String username) {
//        return userRepository.existsByUsername((username).trim());
//    }
//}
