package org.example.elitedrivinglearnersadvancedapp.bo.Custom.impl;


import edu.icet.elite.bo.custom.PaymentBo;
import edu.icet.elite.dao.custom.PaymentDao;
import edu.icet.elite.dao.custom.impl.PaymentDaoImpl;
import edu.icet.elite.dao.custom.StudentDao;
import edu.icet.elite.dao.custom.impl.StudentDaoImpl;
import edu.icet.elite.dto.PaymentDto;
import edu.icet.elite.entity.Payment;
import edu.icet.elite.entity.Student;
import edu.icet.elite.exception.PaymentException;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentBoImpl implements PaymentBo {

    private final PaymentDao paymentDao = new PaymentDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    public void processPayment(PaymentDto paymentDto) throws PaymentException {
        Student student = studentDao.findById(paymentDto.getStudentId())
                .orElseThrow(() -> new PaymentException("Student not found for payment processing."));

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setStatus(paymentDto.getStatus());

        paymentDao.save(payment);
    }

    @Override
    public List<PaymentDto> getPaymentsByStudent(Integer studentId) {
        return paymentDao.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentDao.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PaymentDto mapToDto(Payment payment) {
        return new PaymentDto(
                payment.getPaymentId(),
                payment.getStudent().getStudentId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getStatus()
        );
    }
}