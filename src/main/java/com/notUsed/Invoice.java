//package com.telegrambot.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Set;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Invoice {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "merchant_id", nullable = false)
//    private User merchant;
//
//    @OneToMany(mappedBy = "invoice")
//    private Set<Payment> payments;
//
//    @Column(nullable = false)
//    private Double totalAmount;
//
//    @Column(nullable = false)
//    private String details;
//
//    // Getters and Setters
//}