package com.cat.miao.view.AdoptFragment.network;

import android.util.Log;

import com.cat.miao.model.SignBean;
import com.cat.miao.model.SignupApi;
import com.google.gson.Gson;

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

public class RxRetrofitForSign {
    private static final String Base_url="http://180.76.234.230:8020/";
    private static com.cat.miao.network.RxRetrofitForSign utils=new com.cat.miao.network.RxRetrofitForSign();
    SignupApi signupApi;
    public static com.cat.miao.network.RxRetrofitForSign getInstens(){
        return utils;
    }
    public RxRetrofitForSign(){
        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        signupApi=retrofit.create(SignupApi.class);
    }

    public void getSignInfo(final CallBack call){
        final SignBean signBeanForReturn = new SignBean();

        Map<String, String> map = new HashMap<>();

        map = call.getMap();

        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;"), str);

        Observable<SignBean> dtoObservable = signupApi.getcall(body);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SignBean signBean) {
                        Log.d("error",signBean.getCode());
                        Log.d("error", signBean.getMessage());
                        call.onSuccess(signBean);
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
       void onSuccess(SignBean signBean);
    }
}
