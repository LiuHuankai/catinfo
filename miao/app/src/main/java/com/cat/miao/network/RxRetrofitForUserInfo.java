package com.cat.miao.network;

import android.util.Log;

import com.cat.miao.MyApplication;
import com.cat.miao.model.LoginBean;
import com.cat.miao.model.UserInfoApi;
import com.cat.miao.model.UserInfoBean;
import com.cat.miao.model.UserInfoUpdateBean;
import com.cat.miao.view.userfragment.UserInfo;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.ArrayList;
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

public class RxRetrofitForUserInfo {
    private static final String Base_url="http://180.76.234.230:8020/";
    private static RxRetrofitForUserInfo utils=new RxRetrofitForUserInfo();
    UserInfoApi userInfoApi;

    public static RxRetrofitForUserInfo getInstens(){
        return utils;
    }

    public RxRetrofitForUserInfo(){
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(MyApplication.getInstance()));

        //ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getInstance()));

        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        userInfoApi = retrofit.create(UserInfoApi.class);
    }

    public void getUserInfo(final RxRetrofitForUserInfo.CallBack call, String number){
        Observable<UserInfoBean> dtoObservable= userInfoApi.getCall(number);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfoBean>() {
                    @Override
                    public void accept(UserInfoBean userInfoBean) throws Exception {
                        if(userInfoBean==null)
                        {
                            call.onError();
                        }
                        else
                        {
                            call.onSuccess(userInfoBean);
                        }
                    }
                });

    }

    public void updateInfo(final RxRetrofitForUserInfo.UpdateCallBack call){
        //map转json
        Map<String, String> map = new HashMap<>();
        //map = call.getMap();
        map.put("name", "熊熊");
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), str);

        Observable<UserInfoUpdateBean> dtoObservable = userInfoApi.updataInfo(body);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoUpdateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoUpdateBean userInfoUpdateBean) {
                        call.onSuccess(userInfoUpdateBean);
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
        void onSuccess(UserInfoBean userInfoBean);
        void onError();
    }

    //这是一个回调接口
    public interface  UpdateCallBack{
        Map<String, String> getMap();
        void onSuccess(UserInfoUpdateBean userInfoUpdateBean);
    }
}
