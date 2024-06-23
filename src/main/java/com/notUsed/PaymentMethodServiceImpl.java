//package com.telegrambot.service.Impl;
//
//import com.telegrambot.entity.PaymentMethod;
//import com.telegrambot.repository.InvoiceRepository;
//import com.telegrambot.repository.PaymentMethodRepository;
//import com.telegrambot.service.PaymentMethodService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PaymentMethodServiceImpl implements PaymentMethodService {
//
//    @Autowired
//    private PaymentMethodRepository paymentMethodRepository;
//
//    @Override
//    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
//        return paymentMethodRepository.save(paymentMethod);
//    }
//
//    @Override
//    public List<PaymentMethod> getAllPaymentMethods() {
//        return paymentMethodRepository.findAll();
//    }
//
//    @Override
//    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
//        return paymentMethodRepository.findById(id);
//    }
//
//    @Override
//    public PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod) {
//        return paymentMethodRepository.save(paymentMethod);
//    }
//
//    @Override
//    public void deletePaymentMethod(Long id) {
//        paymentMethodRepository.deleteById(id);
//    }
//}
