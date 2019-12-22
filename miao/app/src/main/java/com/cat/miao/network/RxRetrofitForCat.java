package com.cat.miao.network;

import com.cat.miao.MyApplication;
import com.cat.miao.model.CatApi;
import com.cat.miao.model.CatBean;
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

public class RxRetrofitForCat {
    private static final String Base_url="http://180.76.234.230:8010/";
    private static RxRetrofitForCat utils=new RxRetrofitForCat();
    CatApi catApi;

    public static RxRetrofitForCat getInstens(){
        return utils;
    }

    public RxRetrofitForCat(){
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

        catApi = retrofit.create(CatApi.class);


    }

    public void getEveryCat(final RxRetrofitForCat.CallBack call, int CatID){
        Observable<CatBean> dtoObservable= catApi.getCat(CatID);

        dtoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CatBean>() {
                    @Override
                    public void accept(CatBean catBean) throws Exception {
                        if(catBean==null)
                        {
                            call.onError();
                        }
                        else
                        {
                            call.onSuccess(catBean);
                        }
                    }
                });
    }

    //这是一个回调接口
    public interface  CallBack{
        void onSuccess(CatBean catBean);
        void onError();
    }

}