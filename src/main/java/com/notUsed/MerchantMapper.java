//package com.telegrambot.mapper;
//
//import com.telegrambot.dto.MerchantDTO;
//import com.telegrambot.entity.Merchant;
//
//public class MerchantMapper {
//    public static MerchantDTO toDTO(Merchant merchant) {
//        MerchantDTO dto = new MerchantDTO();
//        dto.setId(merchant.getId());
//        dto.setName(merchant.getName());
//        dto.setEmail(merchant.getEmail());
//        return dto;
//    }
//
//    public static Merchant toEntity(MerchantDTO dto) {
//        Merchant merchant = new Merchant();
//        merchant.setId(dto.getId());
//        merchant.setName(dto.getName());
//        merchant.setEmail(dto.getEmail());
//        return merchant;
//    }
//}
