package com.mirea.kt.ribo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.registerButton.setOnClickListener(v -> {
            String username = Objects.requireNonNull(binding.usernameEdittext.getText()).toString();
            String email = Objects.requireNonNull(binding.emailEdittext.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordEdittext.getText()).toString();
            String confirm_password = Objects.requireNonNull(binding.confirmPasswordEdittext.getText()).toString();
            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty()) {
                if (password.length() >= 6) {
                    if (password.equals(confirm_password)) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        HashMap<String, String> user_info = new HashMap<String, String>() {{
                                            put("username", username);
                                            put("email", email);
                                            put("password", password);
                                        }};
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user_info);
                                        Toast.makeText(getApplicationContext(), R.string.successful_registration, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MessengerActivity.class));
                                    }
                                });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.mismatch_passwords, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.length_passwords, Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
            }
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