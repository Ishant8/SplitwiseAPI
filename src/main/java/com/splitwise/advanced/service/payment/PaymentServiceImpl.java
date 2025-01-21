package com.splitwise.advanced.service.payment;

import com.splitwise.advanced.dto.request.PaymentReqDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.payment.Payment;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.mapper.PaymentPopulator;
import com.splitwise.advanced.repository.CircleRepository;
import com.splitwise.advanced.repository.PaymentRepository;
import com.splitwise.advanced.repository.UserFriendRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    UserFriendRepository userFriendRepository;
    CircleRepository circleRepository;

    PaymentServiceImpl(UserFriendRepository userFriendRepository, CircleRepository circleRepository, PaymentRepository paymentRepository) {
        this.userFriendRepository = userFriendRepository;
        this.circleRepository = circleRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(PaymentReqDto paymentReqDto) {

        Payment payment = new Payment();
        payment.setPaymentAmount(paymentReqDto.getAmount());

        UserFriend userFriend;
        if(paymentReqDto.getPaymentFrom() < paymentReqDto.getPaymentTo()){
            userFriend = updateDues(paymentReqDto.getPaymentFrom(), paymentReqDto.getPaymentTo(), paymentReqDto.getAmount(), paymentReqDto.getCircleName(),payment,true);
        }
        else{
           userFriend = updateDues(paymentReqDto.getPaymentTo(), paymentReqDto.getPaymentFrom(), paymentReqDto.getAmount(), paymentReqDto.getCircleName(),payment,false);
        }
        payment.setUserFriend(userFriend);

        Circle circle = circleRepository.findByName(paymentReqDto.getCircleName());
        payment.setCircle(circle);

        Payment savedPayment = paymentRepository.save(payment);
        System.out.println(savedPayment);

        return savedPayment;
    }

    @Override
    public Payment updatePayment(PaymentReqDto paymentReqDto) {

        PaymentPopulator paymentPopulator= PaymentPopulator.INSTANCE;

        Payment payment = paymentRepository.findById(paymentReqDto.getId()).orElse(null);

        UserFriend userFriend;
        if(paymentReqDto.getPaymentFrom() < paymentReqDto.getPaymentTo())
        {
            userFriend = updateDues(paymentReqDto.getPaymentFrom(), paymentReqDto.getPaymentTo(), paymentReqDto.getAmount(), paymentReqDto.getCircleName(),payment,true);
        }
        else {
            userFriend = updateDues(paymentReqDto.getPaymentTo(), paymentReqDto.getPaymentFrom(), paymentReqDto.getAmount(), paymentReqDto.getCircleName(),payment,false);
        }

        Circle circle = circleRepository.findByName(paymentReqDto.getCircleName());



        paymentPopulator.toPayment(payment, userFriend,circle, paymentReqDto.getAmount());

        return paymentRepository.save(payment);
    }

    public UserFriend updateDues(int smallerId, int biggerId, BigDecimal amount, String circleName,Payment payment, boolean doSubtract){
        UserFriend userFriend = userFriendRepository.findBySmaller_IdAndBigger_Id(smallerId, biggerId);
        userFriend.setMoneyOwed(doSubtract?userFriend.getMoneyOwed().subtract(amount.subtract(payment.getPaymentAmount())):userFriend.getMoneyOwed().add(amount.subtract(payment.getPaymentAmount())));
        userFriend.getUserFriendCircle().stream()
                .filter(ufc -> ufc.getCircle().getName().equals(circleName))
                .findFirst()
                .ifPresent(ufcFiltered -> ufcFiltered.setOwesInGroup(doSubtract?ufcFiltered.getOwesInGroup().subtract(amount.subtract(payment.getPaymentAmount())):ufcFiltered.getOwesInGroup().add(amount.subtract(payment.getPaymentAmount()))));

        if(!circleName.equals(payment.getCircle().getName())){
            userFriend.getUserFriendCircle().stream()
                    .filter(ufc -> ufc.getCircle().getName().equals(payment.getCircle().getName()))
                    .findFirst()
                    .ifPresent(ufcFiltered -> ufcFiltered.setOwesInGroup(!doSubtract?ufcFiltered.getOwesInGroup().subtract(amount.subtract(payment.getPaymentAmount())):ufcFiltered.getOwesInGroup().add(amount.subtract(payment.getPaymentAmount()))));
        }
        return userFriend;
    }
}
