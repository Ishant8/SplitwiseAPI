package com.splitwise.advanced.controller;

import com.splitwise.advanced.dto.request.PaymentReqDto;
import com.splitwise.advanced.dto.response.PaymentRespDto;
import com.splitwise.advanced.entities.payment.Payment;
import com.splitwise.advanced.mapper.PaymentPopulator;
import com.splitwise.advanced.mapper.PaymentPopulatorImpl;
import com.splitwise.advanced.service.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    PaymentService paymentService;
    PaymentPopulator paymentPopulator = PaymentPopulator.INSTANCE;

    PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    public PaymentRespDto addPayment(@RequestBody PaymentReqDto paymentReqDto) {
        return paymentPopulator.populate(paymentService.createPayment(paymentReqDto));
    }

    @PutMapping("/update")
    public PaymentRespDto updatePayment(@RequestBody PaymentReqDto paymentReqDto) {
        return paymentPopulator.populate(paymentService.updatePayment(paymentReqDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {

        if(paymentService.deletePayment(id))
            return new ResponseEntity<>("Payment deleted Successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Payment Deletion Failed", HttpStatus.BAD_REQUEST);
    }
}
