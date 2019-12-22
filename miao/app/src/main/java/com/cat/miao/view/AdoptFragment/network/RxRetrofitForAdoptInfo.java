package com.cat.miao.view.AdoptFragment.network;

import com.cat.miao.MyApplication;
import com.cat.miao.model.AdoptInfoApi;
import com.cat.miao.model.AdoptInfoBean;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitForAdoptInfo {
    private static final String Base_url="http://180.76.234.230:8040/";
    private static com.cat.miao.network.RxRetrofitForAdoptInfo utils=new com.cat.miao.network.RxRetrofitForAdoptInfo();
    AdoptInfoApi adoptInfoApi;

    public static com.cat.miao.network.RxRetrofitForAdoptInfo getInstens(){
        return utils;
    }

    public RxRetrofitForAdoptInfo(){
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

        adoptInfoApi = retrofit.create(AdoptInfoApi.class);


    }

    public void getAdoptInformation(final com.cat.miao.network.RxRetrofitForAdoptInfo.CallBack call){
        Observable<AdoptInfoBean> dtoObservable= adoptInfoApi.getAdoptInfo();

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AdoptInfoBean>() {
                    @Override
                    public void accept(AdoptInfoBean adoptInfoBean) throws Exception {
                        if(adoptInfoBean==null)
                        {
                            call.onError();
                        }
                        else
                        {
                            call.onSuccess(adoptInfoBean);
                        }
                    }
                });
    }

    //这是一个回调接口
    public interface  CallBack{
        void onSuccess(AdoptInfoBean adoptInfoBean);
        void onError();
    }
}
