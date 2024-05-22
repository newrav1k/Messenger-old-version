package com.mirea.kt.ribo.bottom_navigation.profile.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.bottom_navigation.messenger.MessengerFragment;
import com.mirea.kt.ribo.databinding.FragmentFriendsBinding;
import com.mirea.kt.ribo.databinding.FragmentMessengerBinding;
import com.mirea.kt.ribo.databinding.FragmentProfileBinding;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {
    private FragmentFriendsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        loadUsers();
//
        return binding.getRoot();
    }


        public void loadUsers () {
            ArrayList<Friend> friends = new ArrayList<Friend>();
            FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        if (userSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            continue;
                        }

                        String username = userSnapshot.child("username").getValue().toString();
//                        String profileImage = userSnapshot.child("profileImage").getValue().toString();

                        friends.add(new Friend(username));
                    }

                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    binding.recyclerView.setAdapter(new FriendAdapter(friends));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
