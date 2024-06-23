package com.telegrambot.controller;

import com.telegrambot.dto.PaymentDTO;
import com.telegrambot.expection.PaymentException;
import com.telegrambot.mapper.PaymentMapper;
import com.telegrambot.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @PostMapping
    public PaymentDTO createUser(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.createPayment(paymentDTO);
    }

    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.findAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentDTO getPaymentById(@PathVariable Long id) throws PaymentException {
       return  paymentService.findPaymentById(id);
    }

    @PutMapping("/{id}")
    public PaymentDTO updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        paymentDTO.setId(id);
        return paymentService.updatePayment(paymentDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }

}
