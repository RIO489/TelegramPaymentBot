package com.telegrambot.service.Impl;

import com.telegrambot.dto.PayerDTO;
import com.telegrambot.dto.PaymentDTO;
import com.telegrambot.entity.Payer;
import com.telegrambot.entity.Payment;
import com.telegrambot.enums.PaymentStatus;
import com.telegrambot.expection.PayerException;
import com.telegrambot.expection.PaymentException;
import com.telegrambot.mapper.PayerMapper;
import com.telegrambot.mapper.PaymentMapper;
import com.telegrambot.repository.PayerRepository;
import com.telegrambot.repository.PaymentRepository;
import com.telegrambot.service.PayerService;
import com.telegrambot.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PayerRepository payerRepository;

    @Autowired
    private PayerService payerService;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PayerMapper payerMapper;

    @Override
    public PaymentDTO createPayment(PaymentDTO payment) {
        return paymentMapper.toDTO(paymentRepository.save(paymentMapper.toEntity(payment)));
    }

    @Override
    public List<PaymentDTO> findAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    @Override
    public List<PaymentDTO> findPaymentsByPayer(PayerDTO payerDTO) {
        return findAllPayments().stream()
                .filter(p -> p.getPayerId().equals(payerDTO.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO findPaymentById(Long id) throws PaymentException {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDTO)
                .orElseThrow(() -> new PaymentException("Payment not found"));
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO payment) {
        return paymentMapper.toDTO(paymentRepository.save(paymentMapper.toEntity(payment)));
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentDTO confirmPayment(String payerDetails, String paymentId) throws PaymentException {
        Payer payer;
        try {
             payer = payerMapper.toEntity(payerService.findPayers(payerDetails).get(0));
        } catch (PayerException e) {
            throw new PaymentException(e.getMessage());
        }

        Payment payment = paymentRepository.findByPayerId(payer.getId())
                .orElseThrow(() -> new PaymentException("Payment not found with paymentId: " + paymentId + " for payer with details: " + payerDetails));

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
        return paymentMapper.toDTO(payment);
    }
}
