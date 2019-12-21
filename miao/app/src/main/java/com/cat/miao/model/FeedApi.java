package com.cat.miao.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedApi {
    @GET("v1/feed/{id}")
    Observable<FeedBean> getCall(@Path("id") int id);

    @POST("v1/feed/{id}/add")
    Observable<FeedUpdateBean> getUpdateCall(@Path("id") int id);
}
