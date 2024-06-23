package com.telegrambot.service;

import com.telegrambot.dto.PayerDTO;
import com.telegrambot.expection.PayerException;

import java.util.List;
import java.util.Optional;

public interface PayerService {
    PayerDTO createPayer(PayerDTO payment);

    List<PayerDTO> getAllPayers();

    PayerDTO getPayerById(Long id) throws PayerException;

    PayerDTO findByCredentials(String phoneNumber, String password) throws PayerException;
    PayerDTO findByPhoneNumber(String phoneNumber) throws PayerException;

    PayerDTO findByTaxId(String taxid) throws PayerException;
    PayerDTO findByFullName(String fullname) throws PayerException;

    List<PayerDTO> findPayers(String payerDetail) throws PayerException;

    PayerDTO updatePayer(PayerDTO payment);

    void deletePayer(Long id);
}
