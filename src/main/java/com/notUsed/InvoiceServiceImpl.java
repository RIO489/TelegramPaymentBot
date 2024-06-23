//package com.telegrambot.service.Impl;
//
//import com.telegrambot.entity.Invoice;
//import com.telegrambot.repository.InvoiceRepository;
//import com.telegrambot.service.InvoiceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class InvoiceServiceImpl implements InvoiceService {
//
//    @Autowired
//    private InvoiceRepository invoiceRepository;
//
//    @Override
//    public Invoice createInvoice(Invoice invoice) {
//        return invoiceRepository.save(invoice);
//    }
//
//    @Override
//    public List<Invoice> getAllInvoices() {
//        return invoiceRepository.findAll();
//    }
//
//    @Override
//    public Optional<Invoice> getInvoiceById(Long id) {
//        return invoiceRepository.findById(id);
//    }
//
//    @Override
//    public Invoice updateInvoice(Invoice invoice) {
//        return invoiceRepository.save(invoice);
//    }
//
//    @Override
//    public void deleteInvoice(Long id) {
//        invoiceRepository.deleteById(id);
//    }
//}
