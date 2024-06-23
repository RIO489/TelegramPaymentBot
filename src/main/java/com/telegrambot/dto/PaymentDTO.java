package com.telegrambot.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long payerId;
    private Double amount;
    private String paymentPurpose;
    private String recipient;
    private String status;
    private String confirmationPaymentId;
}
