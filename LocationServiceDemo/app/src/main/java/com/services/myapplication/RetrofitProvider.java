package com.services.myapplication;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static final String BASE_URL = "https://dj9o1jkjr6.execute-api.ap-south-1.amazonaws.com/dev/";

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    private static HttpLoggingInterceptor logging
            = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.addConverterFactory(GsonConverterFactory.create());
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final String token,String sessionToken) {
        if (token != null) {
            httpClient.interceptors().clear();
            httpClient.addInterceptor( chain -> {
                Request original = chain.request();
                Request.Builder builder1 = original.newBuilder()
                        .header("userid", token)
                        .header("session", sessionToken);

                Request request = builder1.build();
                return chain.proceed(request);
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

        public static RetrofitService getService(){
            RetrofitService service
                    = RetrofitProvider.createService(RetrofitService.class);

            return service;
        }


    public void setLocationData(User user){
        try{
            user.setUserid("fb3b1009-39c5-44bd-a7f0-0f398c2f52ee");
            user.setSession("f9b58006-cfeb-4bdd-8e22-262149bac018");

            System.out.println("REQUEST OBJECT IS ** "+new Gson().toJson(user));

            Call<UserResponse> callAsync = getService().setlocationdata(user);

            callAsync.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    System.out.println("RESPONSE SUCCESS ******** "+response.body());
                    UserResponse user = response.body();

                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable throwable) {
                    System.out.println("RESPONSE FAILURE ******** "+throwable.getMessage());
                    System.out.println(throwable);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
