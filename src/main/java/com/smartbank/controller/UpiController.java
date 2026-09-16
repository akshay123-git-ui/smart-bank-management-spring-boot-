package com.smartbank.controller;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.google.zxing.common.BitMatrix;
import com.smartbank.entity.Account;
import com.smartbank.service.AccountService;
import com.smartbank.util.BankException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Controller
public class UpiController {

    @Autowired
    private AccountService accountService;


    /** Generates the SmartBank demo UPI QR image. */
    @GetMapping(value = "/upi/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qr(@RequestParam("upiId") String upiId) {
        try {
            String payload = "upi://pay?pa=" + upiId + "&pn=SmartBank&cu=INR";
            BitMatrix matrix = new QRCodeWriter().encode(
                    payload, BarcodeFormat.QR_CODE, 280, 280);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /** Camera/manual UPI scanner page. */
    @GetMapping("/upi/scan")
    public String scan(HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        return "upiScan";
    }

    /** Payment page after a QR scan or manual UPI ID entry. */
    @GetMapping("/upi/pay")
    public String payPage(@RequestParam("upiId") String upiId,
                          HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";

        Account receiver = accountService.getAccountByUpiId(upiId).orElse(null);
        if (receiver == null) {
            model.addAttribute("error", "SmartBank UPI ID not found: " + upiId);
            return "upiPay";
        }

        model.addAttribute("receiver", receiver);
        model.addAttribute("receiverName", receiver.getUser().getFullName());
        model.addAttribute("receiverLast4", receiver.getAccountNumber().substring(receiver.getAccountNumber().length() - 4));
        return "upiPay";
    }

    /**
     * Completes a demo UPI payment. The default demo UPI PIN is 1234.
     * Money is moved between the two SmartBank demo accounts using the same
     * transactional transfer engine as normal account/UPI transfers.
     */
    @PostMapping("/upi/pay")
    public String pay(@RequestParam("upiId") String upiId,
                      @RequestParam BigDecimal amount,
                      @RequestParam("pin") String pin,
                      @RequestParam(required = false) String description,
                      HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Account receiver = accountService.getAccountByUpiId(upiId).orElse(null);
        if (receiver == null) {
            model.addAttribute("error", "SmartBank UPI ID not found.");
            model.addAttribute("upiId", upiId);
            return "upiPay";
        }

        // Existing accounts from the older version have no stored UPI PIN.
        // For this portfolio simulator they use the documented demo PIN 1234.
        boolean validPin;
        if (receiver.getUser() != null && receiver.getUser().getId().equals(userId)) {
            validPin = false;
            model.addAttribute("error", "You cannot pay your own UPI ID.");
            model.addAttribute("receiver", receiver);
            model.addAttribute("receiverName", receiver.getUser().getFullName());
            model.addAttribute("receiverLast4", receiver.getAccountNumber().substring(receiver.getAccountNumber().length() - 4));
            return "upiPay";
        } else {
            validPin = "1234".equals(pin);
        }

        if (!validPin) {
            model.addAttribute("error", "Incorrect demo UPI PIN. Use 1234.");
            model.addAttribute("receiver", receiver);
            model.addAttribute("receiverName", receiver.getUser().getFullName());
            model.addAttribute("receiverLast4", receiver.getAccountNumber().substring(receiver.getAccountNumber().length() - 4));
            return "upiPay";
        }

        try {
            String desc = (description == null || description.trim().isEmpty())
                    ? "UPI payment"
                    : description.trim();
            accountService.transfer(userId, upiId, amount, desc);
            model.addAttribute("success", "Payment of ₹" + amount + " sent successfully.");
            model.addAttribute("receiver", receiver);
            model.addAttribute("receiverName", receiver.getUser().getFullName());
            model.addAttribute("receiverLast4", receiver.getAccountNumber().substring(receiver.getAccountNumber().length() - 4));
            model.addAttribute("paidAmount", amount);
            return "upiPay";
        } catch (BankException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("receiver", receiver);
            model.addAttribute("receiverName", receiver.getUser().getFullName());
            model.addAttribute("receiverLast4", receiver.getAccountNumber().substring(receiver.getAccountNumber().length() - 4));
            return "upiPay";
        }
    }
}
