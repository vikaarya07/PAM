package com.myproject.githubuser3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.myproject.githubuser3.DetailActivity;
import com.myproject.githubuser3.model.UserGithub;
import com.myproject.githubuser3.R;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.UserViewHolder> {

    private final List<UserGithub> userGithubList ;

    public AdapterSearch(List<UserGithub> userGithubList) {
        this.userGithubList = userGithubList;
    }

    @NonNull
    @Override
    public AdapterSearch.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.UserViewHolder holder, int position) {

        UserGithub userGithub = userGithubList.get(position);
        holder.tv_name.setText(userGithub.getLogin());
        holder.tv_url.setText(userGithub.getUrl());
        Glide.with(holder.itemView.getContext())
                .load(userGithub.getAvatarUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return userGithubList.size();
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

            UserGithub userGithub = userGithubList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("DATA_USER",userGithub);
            v.getContext().startActivity(intent);

        }
    }
}

