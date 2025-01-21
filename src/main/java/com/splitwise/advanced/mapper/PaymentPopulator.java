package com.splitwise.advanced.mapper;

import com.splitwise.advanced.dto.request.PaymentReqDto;
import com.splitwise.advanced.dto.response.PaymentRespDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.payment.Payment;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import com.splitwise.advanced.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Mapper(
        uses = {UserFriendPopulator.class}
)
public interface PaymentPopulator {

    PaymentPopulator INSTANCE = Mappers.getMapper(PaymentPopulator.class);

    @Mapping(source = "userFriend", target = "paymentBetween")
    PaymentRespDto populate(Payment payment);

    @Mapping(source = "amount", target = "paymentAmount")
    @Mapping(target = "payment.id", ignore = true)
    Payment toPayment(@MappingTarget Payment payment, UserFriend userFriend, Circle circle, BigDecimal amount);

    default Map<Integer, String>  mapToCircle(Circle circle) {
        Map<Integer, String> map = new HashMap<>();
        map.put(circle.getId(), circle.getName());
        return map;
    }


}
