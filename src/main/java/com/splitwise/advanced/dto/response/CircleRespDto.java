package com.splitwise.advanced.dto.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CircleRespDto {

    private int id;
    private String name;
    private Date createdAt;
    private Map<Integer,String> groupMembers;
    private List<UserFriendCircleRespDto> owedInGroup;

    public CircleRespDto() {
    }

    public CircleRespDto(int id, String name, Date createdAt, Map<Integer, String> groupMembers) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.groupMembers = groupMembers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Map<Integer, String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(Map<Integer, String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<UserFriendCircleRespDto> getOwedInGroup() {
        return owedInGroup;
    }

    public void setOwedInGroup(List<UserFriendCircleRespDto> owedInGroup) {
        this.owedInGroup = owedInGroup;
    }
}
