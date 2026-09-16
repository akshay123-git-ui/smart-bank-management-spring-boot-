package com.smartbank.controller;
import com.smartbank.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired private NotificationService service;
    @GetMapping public String list(HttpSession s,Model m){
        Long id=(Long)s.getAttribute("userId"); if(id==null)return "redirect:/login";
        m.addAttribute("notifications",service.latest(id)); m.addAttribute("unread",service.unreadCount(id)); return "notifications";
    }
    @PostMapping("/read-all") public String readAll(HttpSession s){
        Long id=(Long)s.getAttribute("userId"); if(id!=null)service.markAllRead(id); return "redirect:/notifications";
    }
}
