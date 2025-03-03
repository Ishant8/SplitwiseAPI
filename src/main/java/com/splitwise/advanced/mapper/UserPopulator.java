package com.splitwise.advanced.mapper;

import com.splitwise.advanced.dto.response.UserRespDto;
import com.splitwise.advanced.entities.currency.Currency;
import com.splitwise.advanced.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserPopulator {

    UserPopulator INSTANCE = Mappers.getMapper(UserPopulator.class);

    UserRespDto toUserRespDto(User user);

    default String mapCurrency(Currency currency){
        return currency.getName();
    }
}
