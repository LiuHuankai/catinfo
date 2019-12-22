package com.cat.miao.view.AdoptFragment.network;

import com.cat.miao.MyApplication;
import com.cat.miao.model.CatInfoApi;
import com.cat.miao.model.CatInfoBean;
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

public class RxRetrofitForCatInfo {
    private static final String Base_url="http://180.76.234.230:8010/";
    private static com.cat.miao.network.RxRetrofitForCatInfo utils=new com.cat.miao.network.RxRetrofitForCatInfo();
    CatInfoApi catInfoApi;

    public static com.cat.miao.network.RxRetrofitForCatInfo getInstens(){
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

    public void getCatInformation(final com.cat.miao.network.RxRetrofitForCatInfo.CallBack call, int pagenumber, int sizenumber){
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

    public void getSearchInformation(final com.cat.miao.network.RxRetrofitForCatInfo.CallBack call, int pagenumber, String name, int sizenumber){
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

    //这是一个回调接口
    public interface  CallBack{
        void onSuccess(CatInfoBean catInfoBean);
        void onError();
    }

}
