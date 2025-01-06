package com.splitwise.advanced.mapper;

import com.splitwise.advanced.dto.response.CircleRespDto;
import com.splitwise.advanced.dto.response.UserFriendCircleRespDto;
import com.splitwise.advanced.entities.circle.Circle;
import com.splitwise.advanced.entities.user.User;
import com.splitwise.advanced.entities.usercircle.UserCircle;
import com.splitwise.advanced.entities.userfriendcircle.UserFriendCircle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface CirclePopulator {

    CirclePopulator INSTANCE = Mappers.getMapper(CirclePopulator.class);

    @Mapping(target = "groupMembers", source = "users", qualifiedByName = "mapToGroupMembers")
    @Mapping(target = "owedInGroup", source = "userFriendCircle")
    CircleRespDto populateCircle(Circle circle);

    @Named("mapToGroupMembers")
    default Map<Integer, String> mapToGroupMembers(List<UserCircle> users){
        Map<Integer, String> groupMembers = new HashMap<>();
        for (UserCircle userCircle : users) {
            groupMembers.put(userCircle.getUser().getId(), userCircle.getUser().getFullName());
        }

        return groupMembers;
    }

    default List<UserFriendCircleRespDto> mapToOwedInGroup(List<UserFriendCircle> userFriendCircles){
        List<UserFriendCircleRespDto> owedInGroup = new ArrayList<>();
        for (UserFriendCircle ufc : userFriendCircles) {
            owedInGroup.add(new UserFriendCircleRespDto(
                    ufc.getUserFriend().getSmaller().getId(),
                    ufc.getUserFriend().getSmaller().getFullName(),
                    ufc.getUserFriend().getBigger().getId(),
                    ufc.getUserFriend().getBigger().getFullName(),
                    ufc.getOwesInGroup()
            ));
        }

        return owedInGroup;
    }


}
