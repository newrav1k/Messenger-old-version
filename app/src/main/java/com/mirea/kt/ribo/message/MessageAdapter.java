package com.mirea.kt.ribo.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.mirea.kt.ribo.R;

import java.util.ArrayList;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private ArrayList<Message> messages;

    public MessageAdapter (ArrayList<Message> messages) {
        this.messages = messages;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.messageTv.setText(message.getText());
        holder.dateTv.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (messages.get(position).getOwnerId().equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
//           return R.layout.   сообщение от нашего current пользователя
//        } else {
//            return R.layout.  сообщение не от нашего пользователя
//        }
//    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView messageTv;
        private TextView dateTv;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

//            messageTv = itemView.findViewById(R.id.message_tv);
//            dateTv = itemView.findViewById(R.id.message_date_tv);
        }
    }
}
