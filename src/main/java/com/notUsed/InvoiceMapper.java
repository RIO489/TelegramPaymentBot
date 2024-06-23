//package com.telegrambot.mapper;
//
//import com.telegrambot.dto.InvoiceDTO;
//import com.telegrambot.entity.Invoice;
//import com.telegrambot.entity.Payment;
//import org.springframework.stereotype.Component;
//
//
//
//@Component
//public class InvoiceMapper {
//    public  InvoiceDTO toDTO(Invoice invoice){
//        InvoiceDTO dto = new InvoiceDTO();
//        dto.setId(invoice.getId());
//        dto.setDetails(invoice.getDetails());
//        dto.setMerchantId(invoice.getMerchant().getId());
//        dto.setTotalAmount(invoice.getTotalAmount());
//        return dto;
//    }
//
//    public  Invoice toEntity(InvoiceDTO dto){
//        Invoice invoice = new Invoice();
//        invoice.setId(dto.getId());
//        invoice.setDetails(dto.getDetails());
//        invoice.setTotalAmount(dto.getTotalAmount());
//        return invoice;
//    }
//}
