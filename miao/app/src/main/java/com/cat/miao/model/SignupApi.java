package com.cat.miao.model;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface SignupApi {
    @Headers({
            "accept: */*",
            "Content-Type: application/json"
    })
    @POST("v1/users")
    Observable<SignBean> getcall(@Body RequestBody route);
}
