//package com.telegrambot.controller;
//
//
//import com.telegrambot.dto.PaymentInstructionDTO;
//import com.telegrambot.entity.PaymentInstruction;
//import com.telegrambot.enums.PaymentStatus;
//import com.telegrambot.service.PaymentInstructionService;
//import com.telegrambot.mapper.PaymentInstructionMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/payment-instructions")
//public class PaymentInstructionController {
//
//    @Autowired
//    private PaymentInstructionService paymentInstructionService;
//
//    @PostMapping
//    public PaymentInstructionDTO createPaymentInstruction(@RequestBody PaymentInstructionDTO paymentInstructionDTO) {
//        PaymentInstruction paymentInstruction = PaymentInstructionMapper.toEntity(paymentInstructionDTO);
//        paymentInstruction = paymentInstructionService.createPaymentInstruction(paymentInstruction);
//        return PaymentInstructionMapper.toDTO(paymentInstruction);
//    }
//
//    @GetMapping
//    public List<PaymentInstructionDTO> getAllPaymentInstructions() {
//        List<PaymentInstruction> paymentInstructions = paymentInstructionService.getAllPaymentInstructions();
//        return paymentInstructions.stream().map(PaymentInstructionMapper::toDTO).toList();
//    }
//
//    @GetMapping("/{id}")
//    public PaymentInstructionDTO getPaymentInstructionById(@PathVariable Long id) {
//        Optional<PaymentInstruction> paymentInstruction = paymentInstructionService.getPaymentInstructionById(id);
//        return paymentInstruction.map(PaymentInstructionMapper::toDTO).orElse(null);
//    }
//
//    @PutMapping("/{id}")
//    public PaymentInstructionDTO updatePaymentInstruction(@PathVariable Long id, @RequestBody PaymentInstructionDTO paymentInstructionDTO) {
//        PaymentInstruction paymentInstruction = PaymentInstructionMapper.toEntity(paymentInstructionDTO);
//        paymentInstruction.setId(id);
//        paymentInstruction = paymentInstructionService.updatePaymentInstruction(paymentInstruction);
//        return PaymentInstructionMapper.toDTO(paymentInstruction);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePaymentInstruction(@PathVariable Long id) {
//        paymentInstructionService.deletePaymentInstruction(id);
//    }
//
//    @GetMapping("/status/{status}")
//    public List<PaymentInstructionDTO> getPaymentInstructionsByStatus(@PathVariable PaymentStatus status) {
//        List<PaymentInstruction> paymentInstructions = paymentInstructionService.getPaymentInstructionsByStatus(status);
//        return paymentInstructions.stream().map(PaymentInstructionMapper::toDTO).toList();
//    }
//}
