package com.telegrambot.mapper;

import com.telegrambot.dto.PaymentDTO;
import com.telegrambot.entity.Payer;
import com.telegrambot.entity.Payment;
import com.telegrambot.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentMapper {

    public PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setConfirmationPaymentId(payment.getConfirmationPaymentId());
        dto.setStatus(payment.getStatus().toString());
        dto.setAmount(payment.getAmount().doubleValue());
        dto.setRecipient(payment.getRecipient());
        dto.setPaymentPurpose(payment.getPaymentPurpose());
        dto.setPayerId(payment.getPayer().getId());
        return dto;
    }

    public Payment toEntity(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setPaymentPurpose(dto.getPaymentPurpose());
        payment.setConfirmationPaymentId(dto.getConfirmationPaymentId());
        payment.setRecipient(dto.getRecipient());
        payment.setAmount(BigDecimal.valueOf(dto.getAmount()));
        payment.setStatus(PaymentStatus.valueOf(dto.getStatus()));
        payment.setPayer(new Payer(dto.getPayerId(), null, null, null, null));
        return payment;
    }
}
