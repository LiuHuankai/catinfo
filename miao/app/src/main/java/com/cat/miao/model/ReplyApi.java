package com.cat.miao.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReplyApi {

    @GET("v1/adopt/request")
    Observable<ReplyBean> getReply();
}
