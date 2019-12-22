package com.cat.miao.model;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReplyApi {

    @POST("v1/adopt/request")
    Observable<ReplyBean> postReply(@Body RequestBody route);
}
