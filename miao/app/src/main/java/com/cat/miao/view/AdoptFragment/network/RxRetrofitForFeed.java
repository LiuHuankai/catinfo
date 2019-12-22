package com.cat.miao.view.AdoptFragment.network;

import android.util.Log;

import com.cat.miao.MyApplication;
import com.cat.miao.model.FeedApi;
import com.cat.miao.model.FeedBean;
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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitForFeed {
    private static final String Base_url="http://180.76.234.230:8050/";
    private static com.cat.miao.network.RxRetrofitForFeed utils=new com.cat.miao.network.RxRetrofitForFeed();
    FeedApi feedApi;

    public static com.cat.miao.network.RxRetrofitForFeed getInstens(){
        return utils;
    }

    public RxRetrofitForFeed(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(MyApplication.getInstance()));

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .cookieJar(cookieJar)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        feedApi = retrofit.create(FeedApi.class);
    }

    public void getFeedInfo(final CallBack call, Integer id){
        Observable<FeedBean> dtoObservable = feedApi.getCall(id);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FeedBean>() {
                    @Override
                    public void accept(FeedBean feedBean) throws Exception {
                        if(feedBean==null)
                        {
                            Log.e("Feed", "连接失败");
                        }
                        else
                        {
                            call.onSuccess(feedBean);
                        }
                    }
                });
    }

    public void getUpdateInfo(final UpdateCallBack call, Integer id){
        Observable<FeedUpdateBean> dtoObservable = feedApi.getUpdateCall(id);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedUpdateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FeedUpdateBean feedUpdateBean) {
                        call.onSuccess(feedUpdateBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("feed", "投喂失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    //这是一个回调接口
    public interface  CallBack{
        void onSuccess(FeedBean feedBean);
    }

    public interface UpdateCallBack{
        void onSuccess(FeedUpdateBean feedUpdateBean);
    }
}
