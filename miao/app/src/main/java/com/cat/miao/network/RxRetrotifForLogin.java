package com.cat.miao.network;

import android.util.Log;

import com.cat.miao.MyApplication;
import com.cat.miao.model.LoginApi;
import com.cat.miao.model.LoginBean;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrotifForLogin {
    private static final String Base_url="http://47.103.196.193:8030/";
    private static RxRetrotifForLogin utils=new RxRetrotifForLogin();
    LoginApi loginApi;

    public static RxRetrotifForLogin getInstens(){
        return utils;
    }

    public RxRetrotifForLogin(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(MyApplication.getInstance()));

        //ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getInstance()));

        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .cookieJar(cookieJar)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        loginApi=retrofit.create(LoginApi.class);
    }

    public void getLoginInfo(final CallBack call){
        Map<String, String> map = new HashMap<>();

        map = call.getMap();

        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), str);

        Observable<LoginBean> dtoObservable = loginApi.getcall(body);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        Log.d("error",loginBean.getCode());
                        Log.d("error", loginBean.getMessage());
                        call.onSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error", "死翘翘");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //这是一个回调接口
    public interface  CallBack{
        Map<String, String> getMap();
        void onSuccess(LoginBean loginBean);
    }
}
