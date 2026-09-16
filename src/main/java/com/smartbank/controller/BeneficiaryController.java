package com.smartbank.controller;
import com.smartbank.service.BeneficiaryService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/beneficiaries")
public class BeneficiaryController {
    @Autowired private BeneficiaryService service;
    private Long user(HttpSession s){return (Long)s.getAttribute("userId");}

    @GetMapping
    public String page(HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        m.addAttribute("beneficiaries",service.list(user(s))); return "beneficiaries";
    }
    @PostMapping("/add")
    public String add(@RequestParam String name,@RequestParam String accountNumber,
                      @RequestParam(required=false) String ifsc,@RequestParam(required=false) String upiId,
                      HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        try{service.add(user(s),name,accountNumber,ifsc,upiId);m.addAttribute("success","Beneficiary added.");}
        catch(BankException e){m.addAttribute("error",e.getMessage());}
        return page(s,m);
    }
    @PostMapping("/remove")
    public String remove(@RequestParam Long id,HttpSession s,Model m){
        if(user(s)==null)return "redirect:/login";
        try{service.remove(user(s),id);m.addAttribute("success","Beneficiary removed.");}
        catch(BankException e){m.addAttribute("error",e.getMessage());}
        return page(s,m);
    }
}
