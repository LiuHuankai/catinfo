package com.cat.miao.model;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface AdoptApi {
    @GET("v1/adopt/blog")

    Observable<AdoptBean> getblog();

    @PUT("v1/adopt/blog")

    Observable<AdoptPushBean> putblog(@Body RequestBody route);

    @POST("v1/adopt/blog")

    Observable<AdoptPostBean> postblog(@Body RequestBody route);
}
