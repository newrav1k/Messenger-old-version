/*
package com.mirea.kt.ribo.bottom_navigation.profile.friends;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.bottom_navigation.messenger.MessengerFragment;
import com.mirea.kt.ribo.bottom_navigation.profile.ProfileFragment;
import com.mirea.kt.ribo.bottom_navigation.settings.SettingsFragment;
import com.mirea.kt.ribo.databinding.ActivityMessengerBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FriendsActivity extends AppCompatActivity implements FriendAdapter.OnFriendClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Друзья");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(new Friend("Даня", "Нисанов", 19, null));
        friends.add(new Friend("Рита", "Нисанова", 19, null));
        RecyclerView rcView = findViewById(R.id.recyclerView);
        FriendAdapter adapter = new FriendAdapter(this, friends);
        rcView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcView.setAdapter(adapter);
    }

    @Override
    public void onFriendClick(Friend friend, int position) {
        Toast.makeText(this, "Click on " + friend.getName() + " " + friend.getSurName(), Toast.LENGTH_LONG).show();
    }
}
*/
