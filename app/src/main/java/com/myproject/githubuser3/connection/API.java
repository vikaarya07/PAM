package com.myproject.githubuser3.connection;

import com.myproject.githubuser3.model.ResponseSearch;
import com.myproject.githubuser3.model.UserGithub;
import com.myproject.githubuser3.model.UserGithubDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("/users")
    Call<List<UserGithub>> getGithubUser(@Header("Authorization") String token);

    @GET("/users/{username}/followers")
    Call<List<UserGithub>> getUserFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    Call<List<UserGithub>> getUserFollowing(@Path("username") String username);

    @GET("/search/users")
    Call<ResponseSearch> getGithubSearch(
            @Query("q") String username
    );

    @GET("users/{username}")
    Call<UserGithubDetail> getUserDetail(@Path("username") String username);
}

