package com.mir.c02.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.mir.c02.R;
import com.mir.c02.connector.FragmentEventListener;
import com.mir.c02.dao.UserDao;
import com.mir.c02.dao.UserFactory;
import com.mir.c02.model.User;

public class ListUserFragment extends ListFragment {

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
        return inflater.inflate(R.layout.list_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        userArrayAdapter.addAll(userDao.getAll());

        setListAdapter(userArrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        fragmentEventListener.onUserListClicked(userDao.getUserById(position));
    }
}
