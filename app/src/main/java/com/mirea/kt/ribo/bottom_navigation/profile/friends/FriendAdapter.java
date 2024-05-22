package com.mirea.kt.ribo.bottom_navigation.profile.friends;

import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.bottom_navigation.messenger.MessengerFragment;
import com.mirea.kt.ribo.bottom_navigation.profile.ProfileFragment;
import com.mirea.kt.ribo.bottom_navigation.settings.SettingsFragment;
import com.mirea.kt.ribo.databinding.ActivityMessengerBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.firebase.*;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{

//    interface OnFriendClickListener {
//        void onFriendClick(Friend friend, int position);
//    }

//    private OnFriendClickListener onFriendClickListener;
    private ArrayList<Friend> friends = new ArrayList<>();

    public FriendAdapter(ArrayList<Friend> friends) {
        this.friends = friends;
    }

//    public FriendAdapter(OnFriendClickListener onFriendClickListener, ArrayList<Friend> friends) {
//        this.onFriendClickListener = onFriendClickListener;
//        this.friends = friends;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.name_tv.setText(friend.getUserName());
//        holder.name_tv.setText(String.format("%s", friend.getUserName()));
//        holder.age_tv.setText(String.format("%d лет", friend.getAge()));

        if (!friends.get(position).getProfileImage().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(friends.get(position).getProfileImage()).into(holder.profileImage_iv);
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onFriendClickListener.onFriendClick(friend, holder.getAdapterPosition());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage_iv;
        TextView name_tv;
//        TextView age_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.perosnName);
//            age_tv = itemView.findViewById(R.id.personAge);
            profileImage_iv = itemView.findViewById(R.id.person_image);
        }
    }
}
