package com.mirea.kt.ribo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirea.kt.ribo.databinding.ActivityChatBinding;
import com.mirea.kt.ribo.message.Message;
import com.mirea.kt.ribo.message.MessageAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String chatId = getIntent().getStringExtra("chatId");

        updateView(chatId);

        binding.sendMessage.setOnClickListener(v -> {
            String message = binding.enterMessage.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.message_input_field_cannot_be_empty, Toast.LENGTH_LONG).show();
                return;
            }

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm");
            String date = simpleDateFormat.format(new Date());

            binding.enterMessage.setText("");
            sendMessage(chatId, message, date);
        });

        binding.addFriendButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateView(String chatId) {
        uploadMessages(chatId);
        uploadPartnerInfo();
        uploadFriendStatus();
    }

    public void sendMessage(String chatId, String message, String date) {
        if (chatId == null) {
            return;
        }

        HashMap<String, String> messageInfo = new HashMap<>();
        messageInfo.put("text", message);
        messageInfo.put("ownerId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        messageInfo.put("date", date);

        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(chatId)
                .child("messages").push().setValue(messageInfo);
    }

    private void uploadMessages(String chatId) {
        if (chatId == null) {
            return;
        }

        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(chatId).child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            return;
                        }

                        ArrayList<Message> messages = new ArrayList<>();
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            String messageId = messageSnapshot.getKey();
                            String ownerId = Objects.requireNonNull(messageSnapshot.child("ownerId").getValue()).toString();
                            String text = Objects.requireNonNull(messageSnapshot.child("text").getValue()).toString();
                            String date = Objects.requireNonNull(messageSnapshot.child("date").getValue()).toString();

                            messages.add(new Message(messageId, ownerId, text, date));
                        }

                        binding.messages.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL, false));
                        binding.messages.setAdapter(new MessageAdapter(messages));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void uploadPartnerInfo() {
        String chatId = getIntent().getStringExtra("chatId");
        if (chatId == null) {
            return;
        }

        FirebaseDatabase.getInstance().getReference().child("Chats").child(chatId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId1 = Objects.requireNonNull(snapshot.child("user1").getValue()).toString();
                String userId2 = Objects.requireNonNull(snapshot.child("user2").getValue()).toString();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

                if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid().equals(userId1)) {
                    databaseReference.child(userId2).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            binding.username.setText(Objects.requireNonNull(snapshot.child("username").getValue()).toString());
                            String profile_image = Objects.requireNonNull(snapshot.child("profile_image").getValue()).toString();

                            if (!profile_image.isEmpty()) {
                                Glide.with(getApplicationContext()).load(profile_image).into(binding.profileImage);
                            } else {
                                binding.profileImage.setImageResource(R.drawable.anime_icon);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    databaseReference.child(userId1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            binding.username.setText(Objects.requireNonNull(snapshot.child("username").getValue()).toString());
                            String profile_image = Objects.requireNonNull(snapshot.child("profile_image").getValue()).toString();

                            if (!profile_image.isEmpty()) {
                                Glide.with(getApplicationContext()).load(profile_image).into(binding.profileImage);
                            } else {
                                binding.profileImage.setImageResource(R.drawable.anime_icon);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadFriendStatus() {

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