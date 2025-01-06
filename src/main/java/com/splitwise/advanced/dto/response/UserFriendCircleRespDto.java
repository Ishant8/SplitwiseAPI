package com.splitwise.advanced.dto.response;

import java.math.BigDecimal;

public class UserFriendCircleRespDto {

    private int userId;
    private String userName;
    private int friendId;
    private String friendName;
    private BigDecimal owesInGroup;

    public UserFriendCircleRespDto() {
    }

    public UserFriendCircleRespDto(int userId, String userName, int friendId, String friendName, BigDecimal owesInGroup) {
        this.userId = userId;
        this.userName = userName;
        this.friendId = friendId;
        this.friendName = friendName;
        this.owesInGroup = owesInGroup;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public BigDecimal getOwesInGroup() {
        return owesInGroup;
    }

    public void setOwesInGroup(BigDecimal owesInGroup) {
        this.owesInGroup = owesInGroup;
    }
}
