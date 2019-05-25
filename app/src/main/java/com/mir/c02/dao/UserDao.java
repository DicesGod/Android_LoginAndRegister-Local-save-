package com.mir.c02.dao;

import com.mir.c02.model.User;

import java.util.Collection;
import java.util.LinkedList;

public class UserDao {

    private LinkedList<User> users = new LinkedList<>();

    public UserDao() {
    }

    public UserDao(LinkedList<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        if (users.contains(user)) {
            return false;
        } else {
            users.add(user);
            return true;
        }
    }

    public boolean removeUser(User user) {
        if (!users.contains(user)) {
            return false;
        } else {
            users.remove(user);
            return true;
        }
    }

    public boolean updateUser(User user, User newUser) {
        if (!users.contains(user)) {
            return false;
        } else {
            users.remove(user);
            users.add(newUser);
            return true;
        }
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(User user) {
        if (users.contains(user)) {
            return users.get(users.indexOf(user));
        } else {
            return null;
        }
    }

    public User getUserById(int id) {
        if (users.size() - 1 >= id) {
            return users.get(id);
        } else {
            return null;
        }
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public boolean contains(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        users.clear();
    }

    public Collection<User> getAll() {
        return users;
    }
}
