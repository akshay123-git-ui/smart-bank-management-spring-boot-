package com.smartbank.controller;

import com.smartbank.entity.User;
import com.smartbank.service.UserService;
import com.smartbank.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/** Separate SmartBank app-lock PIN, similar to a mobile banking app passcode. */
@Controller
public class AppPinController {

    @Autowired
    private UserService userService;

    @GetMapping("/app-pin/create")
    public String createPage(HttpSession session) {
        if (session.getAttribute("pendingUserId") == null) return "redirect:/login";
        return "createPin";
    }

    @PostMapping("/app-pin/create")
    public String create(@RequestParam String pin,
                         @RequestParam String confirmPin,
                         HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("pendingUserId");
        if (userId == null) return "redirect:/login";

        if (!pin.matches("\\d{4}")) {
            model.addAttribute("error", "App PIN must be exactly 4 digits.");
            return "createPin";
        }
        if (!pin.equals(confirmPin)) {
            model.addAttribute("error", "PINs do not match.");
            return "createPin";
        }

        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        user.setAppPinHash(PasswordUtil.hash(pin));
        userService.save(user);
        completeLogin(session);
        return redirectAfterPin(session);
    }

    @GetMapping("/app-pin")
    public String pinPage(HttpSession session) {
        if (session.getAttribute("pendingUserId") == null) return "redirect:/login";
        return "appPin";
    }

    @PostMapping("/app-pin")
    public String verify(@RequestParam String pin,
                          HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("pendingUserId");
        if (userId == null) return "redirect:/login";

        if (!pin.matches("\\d{4}")) {
            model.addAttribute("error", "Enter your 4-digit App PIN.");
            return "appPin";
        }

        User user = userService.findById(userId).orElse(null);
        if (user == null || user.getAppPinHash() == null
                || !PasswordUtil.matches(pin, user.getAppPinHash())) {
            model.addAttribute("error", "Incorrect App PIN.");
            return "appPin";
        }

        completeLogin(session);
        return redirectAfterPin(session);
    }

    private void completeLogin(HttpSession session) {
        session.setAttribute("userId", session.getAttribute("pendingUserId"));
        session.setAttribute("userName", session.getAttribute("pendingUserName"));
        session.setAttribute("role", session.getAttribute("pendingRole"));
        session.removeAttribute("pendingUserId");
        session.removeAttribute("pendingUserName");
        session.removeAttribute("pendingRole");
    }

    private String redirectAfterPin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"))
                ? "redirect:/admin/dashboard"
                : "redirect:/dashboard";
    }
}
