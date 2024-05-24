package com.mirea.kt.ribo.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mirea.kt.ribo.users.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ChatUtil {
    public static void createChat(User user) {
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        HashMap<String, String> chatInfo = new HashMap<>();
        chatInfo.put("user1", uid);
        chatInfo.put("user2", user.getUserId());

        String chatId = generateChatId(uid, user.getUserId());
        FirebaseDatabase.getInstance().getReference().child("Chats").child(chatId)
                .setValue(chatInfo);
        addChatIdToUser(uid, chatId);
        addChatIdToUser(user.getUserId(), chatId);
    }

    private static String generateChatId(String userId1, String userId2) {
        String sumUser1User2 = userId1 + userId2;
        char[] charArray = sumUser1User2.toCharArray();
        Arrays.sort(charArray);

        return new String(charArray);
    }

    private static void addChatIdToUser(String userId, String chatId) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(userId)
                .child("chats").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String chats = task.getResult().getValue().toString();

                        if (!chats.contains(chatId)) {
                            String chatsUptd = addIdToString(chats, chatId);

                            FirebaseDatabase.getInstance().getReference().child("Users").child(userId)
                                    .child("chats").setValue(chatsUptd);
                        }
                    }
                });
    }

    private static String addIdToString(String str, String chatId) {
        if (str.isEmpty()) {
            str += chatId;
        } else {
            str += "," + chatId;
        }
        return str;
    }
}
