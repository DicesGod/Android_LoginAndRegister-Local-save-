package com.mir.c02.dao;

public class UserFactory {

    private static UserDao userDao = new UserDao();

    public static UserDao getUserDao() {
        return userDao;
    }
}
