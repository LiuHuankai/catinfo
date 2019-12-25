package com.cat.miao.network;

import android.util.Log;

import com.cat.miao.MyApplication;
import com.cat.miao.model.ReplyApi;
import com.cat.miao.model.ReplyBean;
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
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitForReply {
    private static final String Base_url="http://180.76.234.230:8040/";
    private static RxRetrofitForReply utils=new RxRetrofitForReply();
    ReplyApi replyApi;

    public static RxRetrofitForReply getInstens(){
        return utils;
    }

    public RxRetrofitForReply(){
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

        replyApi = retrofit.create(ReplyApi.class);


    }

    public void postAdoptReply(final CallBack call){
        Map<String, String> map = new HashMap<>();

        map = call.getMap();

        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), str);

        Observable<ReplyBean> dtoObservable = replyApi.postReply(body);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReplyBean>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("replyBean1","onSubscribe");
                        Log.d("replyBean2", "onSubscribe");
                    }

                    @Override
                    public void onNext(ReplyBean replyBean) {
                        Log.d("replyBean1",replyBean.getCode());
                        Log.d("replyBean2", replyBean.getMessage());
                        call.onSuccess(replyBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onError", "失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("replyBean1","onComplete");
                        Log.d("replyBean2", "onComplete");
                    }
                });
    }

    public interface CallBack{
        void onSuccess(ReplyBean replyBean);
        Map<String, String> getMap();
    }

}
