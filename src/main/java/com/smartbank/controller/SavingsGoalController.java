package com.smartbank.controller;
import com.smartbank.service.SavingsGoalService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
@Controller
@RequestMapping("/savings")
public class SavingsGoalController {
    @Autowired private SavingsGoalService service;
    private Long user(HttpSession s){return (Long)s.getAttribute("userId");}
    @GetMapping public String page(HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login"; m.addAttribute("goals",service.list(user(s))); return "savings";
    }
    @PostMapping("/create")
    public String create(@RequestParam String name,@RequestParam BigDecimal targetAmount,
                         @RequestParam(required=false) String targetDate,HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        try{service.create(user(s),name,targetAmount,(targetDate==null||targetDate.isEmpty())?null:LocalDate.parse(targetDate));m.addAttribute("success","Savings goal created.");}
        catch(Exception e){m.addAttribute("error",e instanceof BankException?e.getMessage():"Invalid goal details.");}
        return page(s,m);
    }
    @PostMapping("/add")
    public String add(@RequestParam Long goalId,@RequestParam BigDecimal amount,HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        try{service.addSavings(user(s),goalId,amount);m.addAttribute("success","Savings amount added.");}
        catch(BankException e){m.addAttribute("error",e.getMessage());}
        return page(s,m);
    }
}
