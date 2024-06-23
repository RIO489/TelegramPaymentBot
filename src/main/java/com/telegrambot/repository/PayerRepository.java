package com.telegrambot.repository;

import com.telegrambot.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayerRepository extends JpaRepository<Payer, Long> {
   Optional<Payer> findByPhoneNumberAndPassword(String phoneNumber, String password);
   Optional<Payer> findByPhoneNumber(String phoneNumber);

   Optional<Payer> findByTaxId(String taxId);
   Optional<Payer> findByFullName(String fullname);
}
