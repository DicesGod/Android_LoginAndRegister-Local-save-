package com.mir.c02.connector;

import com.mir.c02.model.User;

public interface FragmentEventListener {

    void onUserAdded(User user);
    void onUserUpdated(User newUser);
    void onUserListClicked(User user);
}
