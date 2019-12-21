package com.cat.miao.model;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LogoutApi {
    @Headers({
            "accept: */*",
            "Content-Type: application/json"
    })
    @POST("v1/logout")
    Observable<LogoutBean> getcall(@Body RequestBody route);
}
