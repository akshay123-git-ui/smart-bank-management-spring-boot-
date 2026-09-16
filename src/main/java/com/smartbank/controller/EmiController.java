package com.smartbank.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class EmiController {
    @GetMapping("/emi")
    public String emi(){return "emi";}
}
