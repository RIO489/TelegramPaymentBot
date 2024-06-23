//package com.telegrambot.controller;
//
//
//import com.telegrambot.dto.MerchantDTO;
//import com.telegrambot.entity.Merchant;
//import com.telegrambot.mapper.MerchantMapper;
//import com.telegrambot.service.MerchantService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/merchants")
//public class MerchantController {
//
//    @Autowired
//    private MerchantService merchantService;
//
//    @PostMapping
//    public MerchantDTO createMerchant(@RequestBody MerchantDTO merchantDTO) {
//        Merchant merchant = MerchantMapper.toEntity(merchantDTO);
//        merchant = merchantService.createMerchant(merchant);
//        return MerchantMapper.toDTO(merchant);
//    }
//
//    @GetMapping
//    public List<MerchantDTO> getAllMerchants() {
//        List<Merchant> merchants = merchantService.getAllMerchants();
//        return merchants.stream().map(MerchantMapper::toDTO).toList();
//    }
//
//    @GetMapping("/{id}")
//    public MerchantDTO getMerchantById(@PathVariable Long id) {
//        Optional<Merchant> merchant = merchantService.getMerchantById(id);
//        return merchant.map(MerchantMapper::toDTO).orElse(null);
//    }
//
//    @PutMapping("/{id}")
//    public MerchantDTO updateMerchant(@PathVariable Long id, @RequestBody MerchantDTO merchantDTO) {
//        Merchant merchant = MerchantMapper.toEntity(merchantDTO);
//        merchant.setId(id);
//        merchant = merchantService.updateMerchant(merchant);
//        return MerchantMapper.toDTO(merchant);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteMerchant(@PathVariable Long id) {
//        merchantService.deleteMerchant(id);
//    }
//}
