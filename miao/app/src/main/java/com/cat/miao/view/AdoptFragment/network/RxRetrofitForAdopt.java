package com.cat.miao.view.AdoptFragment.network;

import android.util.Log;

import com.cat.miao.MyApplication;
import com.cat.miao.model.AdoptApi;
import com.cat.miao.model.AdoptBean;
import com.cat.miao.model.AdoptPostBean;
import com.cat.miao.model.AdoptPushBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitForAdopt {

    private static final String Base_url="http://180.76.234.230:8040/";
    private static com.cat.miao.network.RxRetrofitForAdopt utils=new com.cat.miao.network.RxRetrofitForAdopt();
    AdoptApi adoptApi;

    public static com.cat.miao.network.RxRetrofitForAdopt getInstens(){
        return utils;
    }

    public RxRetrofitForAdopt(){
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

        adoptApi = retrofit.create(AdoptApi.class);


    }

    public void getblogs(final CallBack call){
        Observable<AdoptBean> dtoObservable= adoptApi.getblog();
        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AdoptBean>() {
                    @Override
                    public void accept(AdoptBean adoptBean) throws Exception {
                        if(adoptBean==null)
                        {
                            call.onError();
                        }
                        else
                        {
                            call.onSuccess(adoptBean);
                        }
                    }
                });
    }

    public void pushblogs(final PushCallBack call){
        Map<String, String> map = new HashMap<>();
        map = call.getMap();
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), str);

        Observable<AdoptPushBean> dtoObservable = adoptApi.putblog(body);
        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdoptPushBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("adoptBean1","onSubscribe");
                    }

                    @Override
                    public void onNext(AdoptPushBean adoptPushBean) {
                        call.onSuccess(adoptPushBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("adoptBean1","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("adoptBean1","onComplete");
                    }
                });

    }

    public void postblogs(final PostCallback call){
        Map<String, String> map = new HashMap<>();
        map = call.getMap();
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), str);

        Observable<AdoptPostBean> dtoObservable = adoptApi.postblog(body);
        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdoptPostBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("adoptPostBean1","onSubscribe");
                    }

                    @Override
                    public void onNext(AdoptPostBean adoptPostBean) {
                        call.onSuccess(adoptPostBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("adoptPostBean1","onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("adoptPostBean1","onComplete");
                    }
                });
    }

    public interface CallBack{
        void onSuccess(AdoptBean adoptBean);
        void onError();
    }

    public interface PushCallBack{
        void onSuccess(AdoptPushBean adoptPushBean);
        Map<String, String> getMap();
    }

    public interface PostCallback{
        void onSuccess(AdoptPostBean adoptPostBean);
        Map<String, String> getMap();
    }
}
