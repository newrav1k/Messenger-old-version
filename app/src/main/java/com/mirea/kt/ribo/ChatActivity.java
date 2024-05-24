package com.mirea.kt.ribo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mirea.kt.ribo.databinding.ActivityChatBinding;
import com.mirea.kt.ribo.message.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadMessages();
    }

    private void loadMessages() {
        String chatId = getIntent().getStringExtra("chatId");
        if (chatId == null) return;;

        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(chatId).child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) return;;

                        ArrayList<Message> messages = new ArrayList<>();
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            String messageId = messageSnapshot.getKey();
                            String ownerId = messageSnapshot.child("ownerId").getValue().toString();
                            String text = messageSnapshot.child("text").getValue().toString();
                            String date = messageSnapshot.child("date").getValue().toString();

                            messages.add(new Message(messageId, ownerId, text, date));
                        }

//                        binding.  обращаемся к ресайлкер вью сообщений и вызываем setLayoutManager и Adapter как делали раньше
//                        binding.
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
    }
}