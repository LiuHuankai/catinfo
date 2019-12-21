package com.cat.miao.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatInfoApi {
    @GET("v1/cats")
    Observable<CatInfoBean> getCatInfo(@Query("curPage") int pagenumber, @Query("pageSize") int sizenumber);

    @GET("v1/cats")
    Observable<CatInfoBean> getSearchInfo(@Query("curPage") int pagenumber, @Query("name") String name, @Query("pageSize") int sizenumber);

}
