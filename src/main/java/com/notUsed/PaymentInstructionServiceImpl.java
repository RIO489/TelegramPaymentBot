//package com.telegrambot.service.Impl;
//
//
//import com.telegrambot.entity.PaymentInstruction;
//import com.telegrambot.enums.PaymentStatus;
//import com.telegrambot.repository.PaymentInstructionRepository;
//import com.telegrambot.service.PaymentInstructionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PaymentInstructionServiceImpl implements PaymentInstructionService {
//
//    @Autowired
//    private PaymentInstructionRepository paymentInstructionRepository;
//
//    @Override
//    public PaymentInstruction createPaymentInstruction(PaymentInstruction paymentInstruction) {
//        return paymentInstructionRepository.save(paymentInstruction);
//    }
//
//    @Override
//    public List<PaymentInstruction> getAllPaymentInstructions() {
//        return paymentInstructionRepository.findAll();
//    }
//
//    @Override
//    public Optional<PaymentInstruction> getPaymentInstructionById(Long id) {
//        return paymentInstructionRepository.findById(id);
//    }
//
//    @Override
//    public PaymentInstruction updatePaymentInstruction(PaymentInstruction paymentInstruction) {
//        return paymentInstructionRepository.save(paymentInstruction);
//    }
//
//    @Override
//    public void deletePaymentInstruction(Long id) {
//        paymentInstructionRepository.deleteById(id);
//    }
//
//    @Override
//    public List<PaymentInstruction> getPaymentInstructionsByStatus(PaymentStatus status) {
//        return paymentInstructionRepository.findByStatus(status);
//    }
//}
