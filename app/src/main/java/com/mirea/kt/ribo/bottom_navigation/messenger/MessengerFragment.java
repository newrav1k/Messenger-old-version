package com.mirea.kt.ribo.bottom_navigation.messenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.chats.ChatsFragment;
import com.mirea.kt.ribo.databinding.FragmentMessengerBinding;
import com.mirea.kt.ribo.users.UsersFragment;

import java.util.HashMap;
import java.util.Objects;

public class MessengerFragment extends Fragment {

    private FragmentMessengerBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMessengerBinding.inflate(inflater, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(binding.fragmentMessenger.getId(), new UsersFragment()).commit();
        binding.topNavigationView.setSelectedItemId(R.id.users);

        HashMap<Integer, Fragment> fragments = new HashMap<Integer, Fragment>() {{
            put(R.id.users, new UsersFragment());
            put(R.id.chats, new ChatsFragment());
        }};

        binding.topNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = fragments.get(item.getItemId());
            getActivity().getSupportFragmentManager().beginTransaction().replace(binding.fragmentMessenger.getId(), Objects.requireNonNull(fragment)).commit();
            return true;
        });

        return binding.getRoot();
    }
}