//package com.telegrambot.repository;
//
//import com.telegrambot.entity.PaymentInstruction;
//import com.telegrambot.enums.PaymentStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface PaymentInstructionRepository extends JpaRepository<PaymentInstruction, Long> {
//    List<PaymentInstruction> findByStatus(PaymentStatus status);
//}