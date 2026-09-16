package com.smartbank.controller;
import com.smartbank.entity.Account;
import com.smartbank.service.AccountService;
import com.smartbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.Collections;
@Controller
public class TransactionsController {
    @Autowired private AccountService accountService;
    @Autowired private TransactionService transactionService;
    @GetMapping("/transactions")
    public String all(HttpSession s,Model m){
        Long id=(Long)s.getAttribute("userId"); if(id==null)return "redirect:/login";
        Account a=accountService.getAccountByUserId(id).orElse(null);
        m.addAttribute("transactions",a==null?Collections.emptyList():transactionService.getMiniStatement(a.getId(),100));
        return "transactions";
    }
}
