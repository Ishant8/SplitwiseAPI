package com.splitwise.advanced.service.payment;

import com.splitwise.advanced.dto.request.PaymentReqDto;
import com.splitwise.advanced.entities.payment.Payment;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface PaymentService {

    Payment createPayment(PaymentReqDto paymentReqDto);

    Payment updatePayment(PaymentReqDto paymentReqDto);
}