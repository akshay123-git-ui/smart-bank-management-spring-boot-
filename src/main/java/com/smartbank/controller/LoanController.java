package com.smartbank.controller;

import com.smartbank.service.LoanService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/loans/apply")
    public String showApplyForm(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "loanApply";
    }

    @PostMapping("/loans/apply")
    public String applyForLoan(@RequestParam String loanType,
                                @RequestParam BigDecimal amount,
                                @RequestParam Integer tenureMonths,
                                HttpSession session,
                                Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        try {
            loanService.applyForLoan(
                    userId,
                    loanType,
                    amount,
                    tenureMonths
            );

            model.addAttribute(
                    "success",
                    "Loan application submitted. Track its status on your dashboard."
            );

        } catch (BankException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "loanApply";
    }

    // ================= PAY EMI =================

    @PostMapping("/loans/pay-emi")
    public String payEmi(@RequestParam Long loanId,
                         HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }

        try {
            loanService.payEmi(userId, loanId);

            session.setAttribute(
                    "success",
                    "EMI paid successfully."
            );

        } catch (BankException e) {

            session.setAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/dashboard";
    }
}