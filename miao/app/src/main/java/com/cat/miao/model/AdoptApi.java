package com.cat.miao.model;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface AdoptApi {
    @GET("v1/adopt/blog")

    Observable<AdoptBean> getblog();
}
