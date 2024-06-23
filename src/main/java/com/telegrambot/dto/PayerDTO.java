package com.telegrambot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayerDTO {
    private Long id;
    private String fullName;
    private String taxId;
    private String phoneNumber;
    private String password;
}
