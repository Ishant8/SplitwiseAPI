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

        UserFriend userFriend;
        if(paymentReqDto.getPaymentFrom() < paymentReqDto.getPaymentTo()){
            userFriend = createDues(paymentReqDto.getPaymentFrom(), paymentReqDto.getPaymentTo(), paymentReqDto.getAmount().negate(), paymentReqDto.getCircleName());
            payment.setPaymentAmount(paymentReqDto.getAmount().negate());
        }
        else{
           userFriend = createDues(paymentReqDto.getPaymentTo(), paymentReqDto.getPaymentFrom(), paymentReqDto.getAmount(), paymentReqDto.getCircleName());
           payment.setPaymentAmount(paymentReqDto.getAmount());
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
            userFriend = updateDues(paymentReqDto.getPaymentFrom(), paymentReqDto.getPaymentTo(), paymentReqDto.getAmount().negate(), paymentReqDto.getCircleName(),payment);
            paymentReqDto.setAmount(paymentReqDto.getAmount().negate());
            System.out.println(payment.getPaymentAmount());
        }
        else {
            userFriend = updateDues(paymentReqDto.getPaymentTo(), paymentReqDto.getPaymentFrom(), paymentReqDto.getAmount(), paymentReqDto.getCircleName(),payment);
            paymentReqDto.setAmount(paymentReqDto.getAmount());
        }

        Circle circle = circleRepository.findByName(paymentReqDto.getCircleName());

        paymentPopulator.toPayment(payment, userFriend,circle, paymentReqDto.getAmount());

        return paymentRepository.save(payment);
    }

    @Override
    public boolean deletePayment(int id) {

        Payment payment = paymentRepository.findById(id).orElse(null);
        UserFriend paymentUserFriend = payment.getUserFriend();

        paymentUserFriend.setMoneyOwed(paymentUserFriend.getMoneyOwed().subtract(payment.getPaymentAmount()));
        paymentUserFriend.getUserFriendCircle().stream()
                    .filter(ufc -> ufc.getCircle().getId() == payment.getCircle().getId())
                    .findFirst()
                    .ifPresent(ufc -> ufc.setOwesInGroup(ufc.getOwesInGroup().subtract(payment.getPaymentAmount())));


        userFriendRepository.save(paymentUserFriend);
        paymentRepository.delete(payment);


        return false;
    }

    public UserFriend updateDues(int smallerId, int biggerId, BigDecimal amount, String circleName,Payment payment){

        UserFriend userFriend = userFriendRepository.findBySmaller_IdAndBigger_Id(smallerId, biggerId);

        if(circleName.equals(payment.getCircle().getName()))
        {
            userFriend.setMoneyOwed(userFriend.getMoneyOwed().add(amount.subtract(amount.intValue() > 0 ? payment.getPaymentAmount().negate() : payment.getPaymentAmount())));
            userFriend.getUserFriendCircle().stream()
                    .filter(ufc -> ufc.getCircle().getName().equals(circleName))
                    .findFirst()
                    .ifPresent(ufcFiltered -> ufcFiltered.setOwesInGroup(ufcFiltered.getOwesInGroup().add(amount.subtract(amount.intValue() > 0 ? payment.getPaymentAmount().negate() : payment.getPaymentAmount()))));
        }
        else {
            userFriend.setMoneyOwed(userFriend.getMoneyOwed().add(amount).subtract(amount.intValue() > 0 ? payment.getPaymentAmount().negate() : payment.getPaymentAmount()));
            userFriend.getUserFriendCircle().stream()
                    .filter(ufc -> ufc.getCircle().getName().equals(circleName))
                    .findFirst()
                    .ifPresent(ufcFiltered -> ufcFiltered.setOwesInGroup(ufcFiltered.getOwesInGroup().add(amount)));
        }

        if(!circleName.equals(payment.getCircle().getName())){
            userFriend.getUserFriendCircle().stream()
                    .filter(ufc -> ufc.getCircle().getName().equals(payment.getCircle().getName()))
                    .findFirst()
                    .ifPresent(ufcFiltered -> ufcFiltered.setOwesInGroup(ufcFiltered.getOwesInGroup().subtract(amount.intValue()>0?payment.getPaymentAmount().negate():payment.getPaymentAmount())));
        }
        return userFriend;
    }


    public UserFriend createDues(int smallerId, int biggerId, BigDecimal amount, String circleName){
        UserFriend userFriend = userFriendRepository.findBySmaller_IdAndBigger_Id(smallerId, biggerId);
        userFriend.setMoneyOwed(userFriend.getMoneyOwed().add(amount));
        userFriend.getUserFriendCircle().stream()
                .filter(ufc -> ufc.getCircle().getName().equals(circleName))
                .findFirst()
                .ifPresent(ufcFiltered -> ufcFiltered.setOwesInGroup(ufcFiltered.getOwesInGroup().add(amount)));
        return userFriend;
    }
}
