package com.cat.miao.model;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInfoApi {
    @GET("v1/users")
    Observable<UserInfoBean> getCall(@Query("info") String number);

    @PUT("v1/users")
    Observable<UserInfoUpdateBean> updataInfo(@Body RequestBody route);
}
