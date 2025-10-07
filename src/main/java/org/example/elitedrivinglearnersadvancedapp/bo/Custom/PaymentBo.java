package org.example.elitedrivinglearnersadvancedapp.bo.Custom;

import org.example.elitedrivinglearnersadvancedapp.dto.PaymentDto;

import java.util.List;

public interface PaymentBo {
    void processPayment(PaymentDto paymentDto) throws PaymentException;
    List<PaymentDto> getPaymentsByStudent(Integer studentId);
    List<PaymentDto> getAllPayments();
}
