//package com.telegrambot.mapper;
//
//import com.telegrambot.dto.PaymentMethodDTO;
//import com.telegrambot.dto.UserDTO;
//import com.telegrambot.entity.PaymentMethod;
//import com.telegrambot.entity.User;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PaymentMethodMapper {
//    public  PaymentMethodDTO toDTO(PaymentMethod paymentMethod){
//        PaymentMethodDTO dto = new PaymentMethodDTO();
//        dto.setId(paymentMethod.getId());
//        dto.setName(paymentMethod.getName());
//        return dto;
//    }
//    public  PaymentMethod toEntity(PaymentMethodDTO dto){
//        PaymentMethod paymentMethod = new PaymentMethod();
//        paymentMethod.setId(dto.getId());
//        paymentMethod.setName(dto.getName());
//        return paymentMethod;
//    }
//
//}
