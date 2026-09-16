package com.smartbank.controller;

import com.smartbank.entity.User;
import com.smartbank.service.UserService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                            @RequestParam String email,
                            @RequestParam String phone,
                            @RequestParam String password,
                            Model model) {
        try {
            userService.register(fullName, email, phone, password);
            model.addAttribute("success", "Registration successful. Please log in.");
            return "login";
        } catch (BankException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session,
                         Model model) {

        Optional<User> userOpt = userService.authenticate(email, password);

        if (!userOpt.isPresent()) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }

        User user = userOpt.get();
        // In place of real email-OTP verification (which needs an SMTP/email
        // provider), this stores the authenticated user in the session.
        // Wiring up JavaMailSender for real OTP emails is a natural next
        // step once this base flow works end to end.
        // Password authentication succeeded, but the banking area is still locked.
        // Keep the user in a temporary session until the separate App PIN is verified.
        session.setAttribute("pendingUserId", user.getId());
        session.setAttribute("pendingUserName", user.getFullName());
        session.setAttribute("pendingRole", user.getRole());

        if (user.getAppPinHash() == null || user.getAppPinHash().trim().isEmpty()) {
            return "redirect:/app-pin/create";
        }
        return "redirect:/app-pin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
