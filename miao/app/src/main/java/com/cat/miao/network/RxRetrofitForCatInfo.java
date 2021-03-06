package com.cat.miao.network;

import android.util.Log;

import com.cat.miao.MyApplication;
import com.cat.miao.model.CatInfoApi;
import com.cat.miao.model.CatInfoBean;
import com.cat.miao.model.FeedUpdateBean;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitForCatInfo {
    private static final String Base_url="http://180.76.234.230:8010/";
    private static RxRetrofitForCatInfo utils=new RxRetrofitForCatInfo();
    CatInfoApi catInfoApi;

    public static RxRetrofitForCatInfo getInstens(){
        return utils;
    }

    public RxRetrofitForCatInfo(){
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(MyApplication.getInstance()));

        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        catInfoApi = retrofit.create(CatInfoApi.class);


    }

    public void getCatInformation(final RxRetrofitForCatInfo.CallBack call, int pagenumber, int sizenumber){
        Observable<CatInfoBean> dtoObservable= catInfoApi.getCatInfo(pagenumber,sizenumber);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CatInfoBean>() {
                    @Override
                    public void accept(CatInfoBean catInfoBean) throws Exception {
                        if(catInfoBean==null)
                        {
                            call.onError();
                        }
                        else
                        {
                            call.onSuccess(catInfoBean);
                        }
                    }
                });
    }

    public void getSearchInformation(final RxRetrofitForCatInfo.CallBack call, int pagenumber, String name, int sizenumber){
        Observable<CatInfoBean> dtoObservable= catInfoApi.getSearchInfo(pagenumber,name,sizenumber);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CatInfoBean>() {
                    @Override
                    public void accept(CatInfoBean catInfoBean) throws Exception {
                        if(catInfoBean==null)
                        {
                            call.onError();
                        }
                        else
                        {
                            call.onSuccess(catInfoBean);
                        }
                    }
                });
    }

    public void getInfoByLocate(final RxRetrofitForCatInfo.CallBack call, int pagenumber, String location, int sizenumber){
        Observable<CatInfoBean> dtoObservable= catInfoApi.getInfoByLocation(pagenumber,location,sizenumber);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CatInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CatInfoBean catInfoBean) {
                        call.onSuccess(catInfoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("track", "获取");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //这是一个回调接口
    public interface  CallBack{
        void onSuccess(CatInfoBean catInfoBean);
        void onError();
    }

}
