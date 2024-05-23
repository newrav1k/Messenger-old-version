package com.mirea.kt.ribo.users;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirea.kt.ribo.databinding.FragmentUsersBinding;

import java.util.ArrayList;
import java.util.Comparator;

public class UsersFragment extends Fragment {

    private FragmentUsersBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);

        loadUsers();

        return binding.getRoot();
    }

    private void loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            if (userSnapshot.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                continue;
                            }

                            String username = userSnapshot.child("username").getValue().toString();
                            String profile_image = userSnapshot.child("profile_image").getValue().toString();

                            users.add(new User(username, profile_image));
                        }
                        UserAdapter.onUserClickListener onUserClickListener = new UserAdapter.onUserClickListener() {
                            @Override
                            public void onUserClickListener(User user, int position) {
                                Toast.makeText(getContext(), "Выбран пользователь " + user.getUsername(), Toast.LENGTH_SHORT).show();
                            }
                        };

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            users.sort(Comparator.comparing(User::getUsername));
                        }

                        binding.users.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        binding.users.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                        binding.users.setAdapter(new UserAdapter(users, onUserClickListener));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}