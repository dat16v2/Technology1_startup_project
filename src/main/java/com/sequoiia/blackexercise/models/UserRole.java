package com.sequoiia.blackexercise.models;

public enum UserRole {
    // PUBLIC = 1, ADMIN = 2, USER = 3;
    PUBLIC(1),
    ADMIN(2),
    USER(3);

    private int userCode;

    UserRole(int value) {
        userCode = value;
    }

    public int getUserCode() {
        return this.userCode;
    }
}