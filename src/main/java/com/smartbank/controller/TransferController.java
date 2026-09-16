package com.smartbank.controller;

import com.smartbank.entity.Account;
import com.smartbank.entity.Transaction;
import com.smartbank.service.AccountService;
import com.smartbank.service.TransactionService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class TransferController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer")
    public String showTransferForm(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String recipientIdentifier,
                            @RequestParam BigDecimal amount,
                            @RequestParam(required = false) String description,
                            HttpSession session,
                            Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            accountService.transfer(userId, recipientIdentifier, amount, description);
            model.addAttribute("success", "Transfer of Rs. " + amount + " completed successfully.");
        } catch (BankException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "transfer";
    }

    @GetMapping("/ministatement")
    public String miniStatement(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Account> accountOpt = accountService.getAccountByUserId(userId);
        List<Transaction> transactions = accountOpt
                .map(acc -> transactionService.getMiniStatement(acc.getId()))
                .orElse(java.util.Collections.emptyList());

        model.addAttribute("transactions", transactions);
        return "ministatement";
    }
}
