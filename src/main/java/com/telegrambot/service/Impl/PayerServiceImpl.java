package com.telegrambot.service.Impl;

import com.telegrambot.dto.PayerDTO;
import com.telegrambot.expection.PayerException;
import com.telegrambot.mapper.PayerMapper;
import com.telegrambot.repository.PayerRepository;
import com.telegrambot.service.PayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayerServiceImpl implements PayerService {

    @Autowired
    private PayerRepository payerRepository;
    @Autowired
    private PayerMapper payerMapper;

    @Override
    public PayerDTO createPayer(PayerDTO payer) {
        return payerMapper.toDTO(payerRepository.save(payerMapper.toEntity(payer)));
    }

    @Override
    public List<PayerDTO> getAllPayers() {
        return payerRepository.findAll()
                .stream()
                .map(payerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PayerDTO getPayerById(Long id) throws PayerException {
        return payerRepository.findById(id)
                .map(payerMapper::toDTO)
                .orElseThrow(() -> new PayerException("Payer not found"));
    }

    @Override
    public PayerDTO findByCredentials(String phoneNumber, String password) throws PayerException {
        return payerMapper.toDTO(payerRepository.findByPhoneNumberAndPassword(phoneNumber, password)
                .orElseThrow(() -> new PayerException("Payer not found")));
    }

    @Override
    public PayerDTO findByTaxId(String taxid) throws PayerException {
        return payerMapper.toDTO(payerRepository.findByTaxId(taxid)
                .orElseThrow(() -> new PayerException("Payer not found by tax ID: " + taxid)));
    }

    @Override
    public PayerDTO findByFullName(String fullname) throws PayerException {
        return payerMapper.toDTO(payerRepository.findByFullName(fullname)
                .orElseThrow(() -> new PayerException("Payer not found by full name: " + fullname)));
    }

    @Override
    public PayerDTO findByPhoneNumber(String phoneNumber) throws PayerException {
        return payerMapper.toDTO(payerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new PayerException("Payer not found by this phone:" + phoneNumber)));
    }

    @Override
    public List<PayerDTO> findPayers(String payerDetail) throws PayerException {
        List<PayerDTO> payers = new ArrayList<>();
        try {
            payers.add(findByFullName(payerDetail));
        } catch (PayerException e) {
            // Ignore exception and try next method
        }

        try {
            payers.add(findByTaxId(payerDetail));
        } catch (PayerException e) {
            // Ignore exception and try next method
        }

        try {
            payers.add(findByPhoneNumber(payerDetail));
        } catch (PayerException e) {
            // Ignore exception
        }

        if (payers.isEmpty()) {
            throw new PayerException("Payer not found by detail: " + payerDetail);
        }

        return payers;
    }
    @Override
    public PayerDTO updatePayer(PayerDTO payer) {
        return payerMapper.toDTO(payerRepository.save(payerMapper.toEntity(payer)));
    }

    @Override
    public void deletePayer(Long id) {
        payerRepository.deleteById(id);
    }
}
