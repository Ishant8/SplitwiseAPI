package com.splitwise.advanced.mapper;

import com.splitwise.advanced.dto.response.UserFriendRespDto;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.userfriend.UserFriend;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UserFriendPopulator {

    UserFriendPopulator INSTANCE = Mappers.getMapper(UserFriendPopulator.class);

    UserFriendRespDto populate(UserFriend userFriend);

    default Map<Integer, String> mapUser(User user)
    {
        Map<Integer, String> map = new HashMap<>();
        map.put(user.getId(), user.getFullName());
        return map;
    }
}
