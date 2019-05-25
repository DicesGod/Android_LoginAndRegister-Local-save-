package com.mir.c02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mir.c02.connector.FragmentEventListener;
import com.mir.c02.dao.UserDao;
import com.mir.c02.dao.UserFactory;
import com.mir.c02.fragment.AddUserFragment;
import com.mir.c02.fragment.ListUserFragment;
import com.mir.c02.fragment.UpdateUserFragment;
import com.mir.c02.model.User;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements FragmentEventListener {

    private static final String ADD_USER_FRAGMENT_TAG = "ADD_USER_FRAGMENT";
    private static final String UPDATE_USER_FRAGMENT_TAG = "UPDATE_USER_FRAGMENT";
    private static final String LIST_USER_FRAGMENT_TAG = "LIST_USER_FRAGMENT";

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = UserFactory.getUserDao();
        userDao.addUser(new User("admin@mir.com", "Mir", "Saadati"));
        connectButtons();
    }

    private void connectButtons() {
        // Add User
        final Button addUserButton = findViewById(R.id.addUserFragmentButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserFragment addUserFragment = new AddUserFragment();
                addFragment(R.id.containerFragment, addUserFragment, ADD_USER_FRAGMENT_TAG);
            }
        });
        // Update User
        Button updateUserButton = findViewById(R.id.updateUserFragmentButton);
        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.updateEmailEditText);
                if(userDao.contains(emailEditText.getText().toString())) {
                    UpdateUserFragment updateUserFragment = new UpdateUserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", emailEditText.getText().toString());
                    updateUserFragment.setArguments(bundle);
                    addFragment(R.id.containerFragment, updateUserFragment, UPDATE_USER_FRAGMENT_TAG);
                }
            }
        });
        // List Users
        Button listUserButton = findViewById(R.id.listUserFragmentButton);
        listUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListUserFragment listUserFragment = new ListUserFragment();
                addFragment(R.id.containerFragment, listUserFragment, LIST_USER_FRAGMENT_TAG);
            }
        });
    }

    @Override
    public void onUserAdded(User user) {
        userDao.addUser(user);
        removeFragment(ADD_USER_FRAGMENT_TAG);
    }

    @Override
    public void onUserUpdated(User newUser) {
        EditText emailEditText = findViewById(R.id.updateEmailEditText);
        userDao.updateUser(userDao.getUserByEmail(emailEditText.getText().toString()), newUser);
        removeFragment(UPDATE_USER_FRAGMENT_TAG);
        emailEditText.setText("");
    }

    @Override
    public void onUserListClicked(User user) {
        removeFragment(LIST_USER_FRAGMENT_TAG);
        EditText emailEditText = findViewById(R.id.updateEmailEditText);
        emailEditText.setText(user.getEmail());
        UpdateUserFragment updateUserFragment = new UpdateUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", user.getEmail());
        updateUserFragment.setArguments(bundle);
        addFragment(R.id.containerFragment, updateUserFragment, UPDATE_USER_FRAGMENT_TAG);
    }

    private void addFragment(int containerId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.from_left);
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.addToBackStack("backStack");
        fragmentTransaction.commit();
    }

    private void removeFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.remove(fragmentManager.findFragmentByTag(tag));
        fragmentTransaction.commit();
        fragmentManager.popBackStack();
    }
}
