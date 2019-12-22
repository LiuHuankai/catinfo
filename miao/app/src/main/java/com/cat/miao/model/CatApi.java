package com.cat.miao.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CatApi {

    @GET("v1/cats/{catID}")
    Observable<CatBean> getCat(@Path("catID") int CatID);
}
