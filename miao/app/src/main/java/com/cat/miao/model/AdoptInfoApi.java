package com.cat.miao.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AdoptInfoApi {

    @GET("v1/adopt/request")
    Observable<AdoptInfoBean> getAdoptInfo();
}
