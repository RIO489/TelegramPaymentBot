//package com.telegrambot.service.Impl;
//
//import com.telegrambot.entity.Merchant;
//import com.telegrambot.repository.MerchantRepository;
//import com.telegrambot.service.MerchantService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class MerchantServiceImpl implements MerchantService {
//
//    @Autowired
//    private MerchantRepository merchantRepository;
//
//    @Override
//    public Merchant createMerchant(Merchant merchant) {
//        return merchantRepository.save(merchant);
//    }
//
//    @Override
//    public List<Merchant> getAllMerchants() {
//        return merchantRepository.findAll();
//    }
//
//    @Override
//    public Optional<Merchant> getMerchantById(Long id) {
//        return merchantRepository.findById(id);
//    }
//
//    @Override
//    public Merchant updateMerchant(Merchant merchant) {
//        return merchantRepository.save(merchant);
//    }
//
//    @Override
//    public void deleteMerchant(Long id) {
//        merchantRepository.deleteById(id);
//    }
//}
