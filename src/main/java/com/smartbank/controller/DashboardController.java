package com.smartbank.controller;

import com.smartbank.entity.Account;
import com.smartbank.entity.Loan;
import com.smartbank.entity.Transaction;
import com.smartbank.service.AccountService;
import com.smartbank.service.LoanService;
import com.smartbank.service.TransactionService;
import com.smartbank.service.NotificationService;
import com.smartbank.service.SavingsGoalService;
import com.smartbank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LoanService loanService;

    @Autowired private NotificationService notificationService;
    @Autowired private SavingsGoalService savingsGoalService;
    @Autowired private CardService cardService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Account> accountOpt = accountService.getAccountByUserId(userId);
        model.addAttribute("account", accountOpt.orElse(null));

        List<Transaction> recentTransactions = accountOpt
                .map(acc -> transactionService.getMiniStatement(acc.getId()))
                .orElse(Collections.emptyList());
        model.addAttribute("recentTransactions", recentTransactions);

        List<Loan> loans = loanService.getLoansForUser(userId);
        model.addAttribute("loans", loans);
        model.addAttribute("unreadNotifications", notificationService.unreadCount(userId));
        model.addAttribute("goals", savingsGoalService.list(userId));
        model.addAttribute("card", cardService.getOrCreate(userId));

        return "dashboard";
    }
}
