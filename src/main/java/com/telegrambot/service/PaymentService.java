package com.telegrambot.service;

import com.telegrambot.dto.PayerDTO;
import com.telegrambot.dto.PaymentDTO;
import com.telegrambot.expection.PaymentException;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO payment);

    List< PaymentDTO> findAllPayments();

    PaymentDTO findPaymentById(Long id) throws PaymentException;

    List< PaymentDTO> findPaymentsByPayer(PayerDTO payerDTO);

    PaymentDTO updatePayment(PaymentDTO payment);

    void deletePayment(Long id);
    PaymentDTO confirmPayment(String taxId, String paymentId) throws PaymentException;
}
