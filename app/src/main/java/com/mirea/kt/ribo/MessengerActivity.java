package com.mirea.kt.ribo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mirea.kt.ribo.bottom_navigation.messenger.MessengerFragment;
import com.mirea.kt.ribo.bottom_navigation.profile.ProfileFragment;
import com.mirea.kt.ribo.bottom_navigation.settings.SettingsFragment;
import com.mirea.kt.ribo.databinding.ActivityMessengerBinding;

import java.util.HashMap;
import java.util.Objects;

public class MessengerActivity extends AppCompatActivity {

    private ActivityMessengerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessengerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), new MessengerFragment()).commit();
        binding.bottomNavigation.setSelectedItemId(R.id.action_mail_outline);

        HashMap<Integer, Fragment> fragments = new HashMap<Integer, Fragment>() {{
            put(R.id.action_person, new ProfileFragment());
            put(R.id.action_mail_outline, new MessengerFragment());
            put(R.id.action_settings, new SettingsFragment());
        }};

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = fragments.get(item.getItemId());
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), Objects.requireNonNull(fragment)).commit();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}