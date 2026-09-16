package com.smartbank.controller;

import com.smartbank.entity.Loan;
import com.smartbank.entity.User;
import com.smartbank.service.LoanService;
import com.smartbank.service.UserService;
import com.smartbank.service.TransactionService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

    @Autowired private TransactionService transactionService;

    // A single shared helper to keep every admin endpoint guarded.
    // A cleaner version of this (used across many controllers) would be a
    // Spring HandlerInterceptor - worth building once you're comfortable
    // with this simpler per-method check.
    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        List<Loan> pendingLoans = loanService.getPendingLoans();
        List<User> customers = userService.listAllCustomers();
        model.addAttribute("pendingCount", pendingLoans.size());
        model.addAttribute("customerCount", customers.size());
        return "adminDashboard";
    }

    @GetMapping("/customers")
    public String manageCustomers(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("customers", userService.listAllCustomers());
        return "manageCustomers";
    }

    @GetMapping("/loans")
    public String allLoans(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("loans", loanService.getAllLoans());
        return "approveLoans";
    }

    @PostMapping("/loans/decide")
    public String decideLoan(@RequestParam Long loanId,
                              @RequestParam String decision, // "APPROVE" or "REJECT"
                              @RequestParam(required = false) String remarks,
                              HttpSession session,
                              Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        try {
            loanService.decideLoan(loanId, "APPROVE".equalsIgnoreCase(decision), remarks);
        } catch (BankException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/loans";
    }

    @GetMapping("/transactions")
    public String transactions(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("transactions", transactionService.getRecentForAdmin(100));
        return "adminTransactions";
    }

    @GetMapping("/reports")
    public String reports(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        List<Loan> allLoans = loanService.getAllLoans();
        BigDecimal totalApprovedAmount = allLoans.stream()
                .filter(l -> "APPROVED".equals(l.getStatus())
                        || "CLOSED".equals(l.getStatus()))
                .map(Loan::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long approvedCount = allLoans.stream()
                .filter(l -> "APPROVED".equals(l.getStatus())
                        || "CLOSED".equals(l.getStatus()))
                .count();
        long rejectedCount = allLoans.stream().filter(l -> "REJECTED".equals(l.getStatus())).count();
        long pendingCount = allLoans.stream().filter(l -> "PENDING".equals(l.getStatus())).count();

        model.addAttribute("totalApprovedAmount", totalApprovedAmount);
        model.addAttribute("approvedCount", approvedCount);
        model.addAttribute("rejectedCount", rejectedCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("totalCustomers", userService.listAllCustomers().size());

        return "reports";
    }
}
