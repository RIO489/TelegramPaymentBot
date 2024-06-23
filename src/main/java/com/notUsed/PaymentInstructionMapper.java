//package com.telegrambot.mapper;
//
//import com.telegrambot.dto.PaymentInstructionDTO;
//import com.telegrambot.entity.PaymentInstruction;
//import com.telegrambot.enums.PaymentStatus;
//
//public class PaymentInstructionMapper {
//
//    public static PaymentInstructionDTO toDTO(PaymentInstruction paymentInstruction) {
//        PaymentInstructionDTO dto = new PaymentInstructionDTO();
//        dto.setId(paymentInstruction.getId());
//        dto.setDetails(paymentInstruction.getDetails());
//        dto.setAmount(paymentInstruction.getAmount());
//        dto.setStatus(paymentInstruction.getStatus().name());
//        return dto;
//    }
//    public static PaymentInstruction toEntity(PaymentInstructionDTO dto) {
//        PaymentInstruction paymentInstruction = new PaymentInstruction();
//        paymentInstruction.setId(dto.getId());
//        paymentInstruction.setDetails(dto.getDetails());
//        paymentInstruction.setAmount(dto.getAmount());
//        paymentInstruction.setStatus(PaymentStatus.valueOf(dto.getStatus()));
//        return paymentInstruction;
//    }
//}
