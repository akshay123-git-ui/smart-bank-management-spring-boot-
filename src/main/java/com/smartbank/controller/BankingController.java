package com.smartbank.controller;
import com.smartbank.entity.Account;
import com.smartbank.service.AccountService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class BankingController {
    @Autowired private AccountService accountService;

    private Long user(HttpSession s){return (Long)s.getAttribute("userId");}

    @GetMapping("/banking")
    public String banking(HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        m.addAttribute("account",accountService.getAccountByUserId(user(s)).orElse(null));
        return "banking";
    }
    @PostMapping("/banking/deposit")
    public String deposit(@RequestParam BigDecimal amount,@RequestParam(required=false) String description,
                          HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        try{accountService.deposit(user(s),amount,description);m.addAttribute("success","Deposit recorded successfully.");}
        catch(BankException e){m.addAttribute("error",e.getMessage());}
        return banking(s,m);
    }
    @PostMapping("/banking/withdraw")
    public String withdraw(@RequestParam BigDecimal amount,@RequestParam(required=false) String description,
                           HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        try{accountService.withdraw(user(s),amount,description);m.addAttribute("success","Withdrawal recorded successfully.");}
        catch(BankException e){m.addAttribute("error",e.getMessage());}
        return banking(s,m);
    }
}
