package me.qiancheng.qianworks.meicai.service;

import me.qiancheng.qianworks.meicai.constant.MeicanAPIConstant;
import me.qiancheng.qianworks.meicai.model.Status;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by iamya on 6/24/2016.
 */
public interface OrderService {

    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @POST(MeicanAPIConstant.API_VERSION+ MeicanAPIConstant.URI_ORDER_CREATE)
    Call<Status> order(@Header("Authorization") String authorization, @QueryMap Map<String, String> options);

}
