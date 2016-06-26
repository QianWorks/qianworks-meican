package me.qiancheng.qianworks.meicai.base;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MeicanInterceptors implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MeicanInterceptors.class);

    @Override
    public Response intercept(Chain chain) throws IOException {

        //封装headers
        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json") //添加请求头信息
                .build();
        Headers headers = request.headers();
        //打印
        LOG.debug("Content-Type is : " + headers.get("Content-Type"));
        String requestUrl = request.url().toString(); //获取请求url地址
        String methodStr = request.method(); //获取请求方式
        RequestBody body = request.body(); //获取请求body
        String bodyStr = (body==null?"":body.toString());
        //打印Request数据
        LOG.debug("Request Url is :" + requestUrl );
        LOG.debug("Method is : " + methodStr );
        LOG.debug("Request Body is :" + bodyStr );

        Response response = chain.proceed(request);
        if (response != null) {
            LOG.debug("Response is not null");
        } else {
            LOG.debug("Respong is null");
        }
        return response;

    }
}