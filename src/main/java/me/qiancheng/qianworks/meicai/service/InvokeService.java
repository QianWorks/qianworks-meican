/*
* Copyright 2013-2016 Fenqile  Co ., Ltd. All Rights Reserved.
*
*/

package me.qiancheng.qianworks.meicai.service;

import me.qiancheng.qianworks.meicai.constant.MeicanAPIConstant;
import me.qiancheng.qianworks.meicai.model.Status;
import me.qiancheng.qianworks.meicai.model.Token;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by  josephyan<千橙> on 06-Jul-16.
 */
public interface InvokeService {

    /**  oauth */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @GET(MeicanAPIConstant.API_VERSION+ MeicanAPIConstant.URI_OAUTH)
    Call<Token> oauth(@QueryMap Map<String, String> options);

    /**
     * 刷新token
     */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @GET(MeicanAPIConstant.API_VERSION + MeicanAPIConstant.URI_OAUTH)
    Call<Token> refreshToken(@QueryMap Map<String, String> options);


    /**  下单 */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @POST(MeicanAPIConstant.API_VERSION+ MeicanAPIConstant.URI_ORDER_CREATE)
    Call<Status> order(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);


    /**
     * 最近一单
     */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @POST(MeicanAPIConstant.API_VERSION + MeicanAPIConstant.URI_ORDER_CREATE)
    Call<Status> lastOrder(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);


    /**
     * 订单列表
     */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @POST(MeicanAPIConstant.API_VERSION + MeicanAPIConstant.URI_ORDER_CREATE)
    Call<Status> listOrder(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);


    /**
     * 最喜欢的
     */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @POST(MeicanAPIConstant.API_VERSION + MeicanAPIConstant.URI_ORDER_CREATE)
    Call<Status> favorite(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);


    /**
     * 其他人最喜欢的
     */
    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @POST(MeicanAPIConstant.API_VERSION + MeicanAPIConstant.URI_ORDER_CREATE)
    Call<Status> othersFavorite(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);

}
 
