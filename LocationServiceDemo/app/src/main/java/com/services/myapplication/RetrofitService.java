package com.services.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

//    @GET("/users")
//    public Call<List<User>> getUsers(
//            @Query("per_page") int per_page,
//            @Query("page") int page);
//
    @POST("/users/{username}")
    public Call<User> getUser(@Path("username") String username);

    @POST("/user/setlocationdata")
    public Call<UserResponse> setlocationdata(@Body User user);


}
