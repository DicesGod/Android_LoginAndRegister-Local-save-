package com.mir.c02.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mir.c02.R;
import com.mir.c02.connector.FragmentEventListener;
import com.mir.c02.dao.UserDao;
import com.mir.c02.dao.UserFactory;
import com.mir.c02.model.User;

import java.util.Arrays;

public class UpdateUserFragment extends Fragment {

    private FragmentEventListener fragmentEventListener;
    private UserDao userDao;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentEventListener = (FragmentEventListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = UserFactory.getUserDao();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_user_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = this.getArguments().getString("email");
        User user = userDao.getUserByEmail(email);
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        String displayEmail = "Email: " + user.getEmail();
        String displayName = "Name: " + user.getFirstName() + " " + user.getLastName();
        emailTextView.setText(displayEmail);
        nameTextView.setText(displayName);
        Button registerButton = view.findViewById(R.id.updateButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
                EditText emailEditText = getView().findViewById(R.id.emailEditTextUpdate);
                EditText nameEditText = getView().findViewById(R.id.nameEditTextUpdate);
                String email = emailEditText.getText().toString();
                String name = nameEditText.getText().toString();
                name += " NA";
                String firstName = Arrays.asList(name.split(" ")).get(0);
                String lastName = Arrays.asList(name.split(" ")).get(1);
                fragmentEventListener.onUserUpdated(new User(email, firstName, lastName));
            }
        });
    }
}
