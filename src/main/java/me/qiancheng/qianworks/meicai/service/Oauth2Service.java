package me.qiancheng.qianworks.meicai.service;

import me.qiancheng.qianworks.meicai.constant.MeicanAPIConstant;
import me.qiancheng.qianworks.meicai.model.Token;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by iamya on 6/24/2016.
 */
public interface Oauth2Service {

    @Headers({
            "Accept: application/json",
            "Accept-Charset: utf-8",
            "User-Agent: okhttp/2.4.0"
    })
    @GET(MeicanAPIConstant.API_VERSION+ MeicanAPIConstant.URI_OAUTH)
    Call<Token> oauth(@QueryMap Map<String, String> options);

}
