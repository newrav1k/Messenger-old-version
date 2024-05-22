package com.mirea.kt.ribo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.mirea.kt.ribo.databinding.ActivityRegisterBinding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private Uri filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
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
                                            put("email", email);
                                            put("username", username);
                                            put("profile_image", "");
                                            put("status", "Привет! Я есть Грут!");
                                        }};
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user_info);
                                        Toast.makeText(getApplicationContext(), R.string.successful_registration, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MessengerActivity.class));
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), R.string.account_already_exists, Toast.LENGTH_LONG).show();
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

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    filePath = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(
                                        getContentResolver(),
                                        filePath
                                );
                        binding.profileImage.setImageBitmap(bitmap);
                    } catch (IOException exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    uploadImage();
                }
            });

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLaunch.launch(intent);
    }

    private void uploadImage() {
        if (filePath != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseStorage.getInstance().getReference().child("images/" + uid)
                    .putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), R.string.photo_uploaded_successfully, Toast.LENGTH_LONG).show();

                            FirebaseStorage.getInstance().getReference().child("images/" + uid).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("profile_image").setValue(uri.toString());
                                        }
                                    });
                        }
                    });
        }
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