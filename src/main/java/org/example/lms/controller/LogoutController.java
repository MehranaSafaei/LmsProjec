package org.example.lms.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/api/auth/lms")
public class LogoutController {


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
    }
//    @GetMapping("/logout")
//    public String logout(RedirectAttributes redirectAttributes) {
//
//        SecurityContextHolder.clearContext();
//        redirectAttributes.addFlashAttribute("message", "خروج با موفقیت انجام شد!");
//        return "redirect:/login";
//    }
}