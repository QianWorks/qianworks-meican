package me.qiancheng.qianworks.meicai.util;

import me.qiancheng.qianworks.meicai.base.MeicanInterceptors;
import me.qiancheng.qianworks.meicai.constant.MeicanAPIConstant;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iamya on 6/26/2016.
 */
public class RetrofitServiceFactory<T> {

    private static final String baseUrl= MeicanAPIConstant.API_HOST;

    private static Retrofit retrofit;

    static {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new MeicanInterceptors())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public T getService(Class<T> clazz){

        return retrofit.create(clazz);
    }
}
