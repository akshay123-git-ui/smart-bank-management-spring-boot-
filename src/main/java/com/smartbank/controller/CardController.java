package com.smartbank.controller;
import com.smartbank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
@Controller
public class CardController {
    @Autowired private CardService cardService;
    @GetMapping("/card")
    public String card(HttpSession s,Model m){
        Long id=(Long)s.getAttribute("userId"); if(id==null)return "redirect:/login";
        m.addAttribute("card",cardService.getOrCreate(id)); return "card";
    }
}
