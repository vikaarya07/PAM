package com.myproject.githubuser3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.myproject.githubuser3.adapter.AdapterUser;
import com.myproject.githubuser3.connection.API;
import com.myproject.githubuser3.connection.RetrofitConfig;
import com.myproject.githubuser3.model.UserGithub;
import com.myproject.githubuser3.R;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        setDataFollowers(view);
        return view;

    }

    private void setDataFollowers(View view){

        recyclerView = view.findViewById(R.id.rv_user);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UserGithub userGithub = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra("DATA_USER");
        API api = RetrofitConfig.getRetrofit().create(API.class);
        Call<List<UserGithub>> call = api.getUserFollowers(Objects.requireNonNull(userGithub).getLogin());
        call.enqueue(new Callback<List<UserGithub>>() {

            @Override
            public void onResponse(Call<List<UserGithub>> call, Response<List<UserGithub>> response) {

                AdapterUser adapterUser = new AdapterUser(getContext(),response.body());
                recyclerView.setAdapter(adapterUser);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<UserGithub>> call, Throwable t) {

            }
        });
    }
}