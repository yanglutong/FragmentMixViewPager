package com.lutong.Retrofit;







import com.lutong.Constant.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by 南志强 on 2018/1/26.
 */

public class RetrofitFactory {
    private static RetrofitFactory retrofitFactory;
    private static RetrofitService retrofitService;
    private RetrofitFactory(){
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.HeaderInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .build();
        Retrofit mRetrofit=new Retrofit.Builder()
                .baseUrl(MyURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        retrofitService=mRetrofit.create(RetrofitService.class);

    }

    public static RetrofitFactory getInstence(){
        if (retrofitFactory ==null){
            synchronized (RetrofitFactory.class) {
                if (retrofitFactory == null)
                    retrofitFactory = new RetrofitFactory();
            }

        }
        return retrofitFactory;
    }
    public  RetrofitService API(){
        return retrofitService;
    }
}
