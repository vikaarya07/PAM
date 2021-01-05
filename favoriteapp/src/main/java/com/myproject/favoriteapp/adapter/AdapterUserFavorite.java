package com.myproject.favoriteapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myproject.favoriteapp.model.UserGithub;
import com.myproject.favoriteapp.R;
import com.myproject.favoriteapp.DetailActivity;

public class AdapterUserFavorite extends RecyclerView.Adapter<AdapterUserFavorite.UserViewHolder> {

    private final Context c;
    private Cursor cursor;

    public AdapterUserFavorite(Context c) {
        this.c = c;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    private UserGithub getItemData(int position){

        if (!cursor.moveToPosition(position)){

            throw new IllegalStateException("INVALID");

        }

        return new UserGithub(cursor);

    }

    @NonNull
    @Override
    public AdapterUserFavorite.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserFavorite.UserViewHolder holder, int position) {

        UserGithub userGithub = getItemData(position);
        holder.tv_name.setText(userGithub.getLogin());
        holder.tv_url.setText(userGithub.getHtmlUrl());
        Glide.with(holder.itemView.getContext())
                .load(userGithub.getAvatarUrl())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {

        if (cursor == null) return 0;
        return cursor.getCount();

    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView tv_url;
        TextView tv_name;

        UserViewHolder(@NonNull View itemView) {

            super(itemView);
            img = itemView.findViewById(R.id.img_user);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_url = itemView.findViewById(R.id.tv_url);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            UserGithub userGithub = getItemData(getAdapterPosition());
            Intent intent = new Intent(c, DetailActivity.class);
            intent.putExtra("DATA_USER",userGithub);
            v.getContext().startActivity(intent);

        }
    }
}
