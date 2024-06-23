package com.telegrambot.expection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayerException extends Exception {
    private int code;
    private String message;


    public PayerException(String message) {
        this.code = 400;
        this.message = message;
    }
}
