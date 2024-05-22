package com.mirea.kt.ribo.bottom_navigation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.bottom_navigation.messenger.MessengerFragment;

import com.mirea.kt.ribo.bottom_navigation.profile.friends.FriendsFragment;
import com.mirea.kt.ribo.databinding.FragmentMessengerBinding;
import com.mirea.kt.ribo.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {


    private FragmentProfileBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.friendsButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FriendsFragment()).commit();
        });
        return binding.getRoot();
    }
}