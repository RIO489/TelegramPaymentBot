package com.telegrambot.mapper;

import com.telegrambot.dto.PayerDTO;
import com.telegrambot.entity.Payer;
import org.springframework.stereotype.Component;

@Component
public class PayerMapper {
    public PayerDTO toDTO(Payer payer) {
        PayerDTO dto = new PayerDTO();
        dto.setId(payer.getId());
        dto.setPassword(payer.getPassword());
        dto.setFullName(payer.getFullName());
        dto.setTaxId(payer.getTaxId());
        dto.setPhoneNumber(payer.getPhoneNumber());
        return dto;
    }
    public Payer toEntity(PayerDTO dto) {
        Payer payer = new Payer();
        payer.setId(dto.getId());
        payer.setFullName(dto.getFullName());
        payer.setTaxId(dto.getTaxId());
        payer.setPhoneNumber(dto.getPhoneNumber());
        payer.setPassword(dto.getPassword());
        return payer;
    }
}
