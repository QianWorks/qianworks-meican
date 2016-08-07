package me.qiancheng.qianworks.meicai.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import me.qiancheng.qianworks.meicai.constant.MeicanAPIConstant;
import me.qiancheng.qianworks.meicai.model.Args;
import me.qiancheng.qianworks.meicai.model.Return;
import me.qiancheng.qianworks.meicai.model.Status;
import me.qiancheng.qianworks.meicai.model.Token;
import me.qiancheng.qianworks.meicai.service.MailService;
import me.qiancheng.qianworks.meicai.service.Oauth2Service;
import me.qiancheng.qianworks.meicai.service.OrderService;
import me.qiancheng.qianworks.meicai.util.RetrofitServiceFactory;
import me.qiancheng.qianworks.meicai.util.StringHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledTasks {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private AsyncMailWorker mailWorker;
    @Autowired
    private MailService mailService;

    private Oauth2Service oauth2Service = new RetrofitServiceFactory<Oauth2Service>().getService(Oauth2Service.class);
    private OrderService orderService = new RetrofitServiceFactory<OrderService>().getService(OrderService.class);


    @Scheduled(fixedRate = 1000 * 3600 * 24, initialDelay = 10 * 1000)
    public void checkOrder() throws IOException {
        LOG.info("reportCurrentTime1 - " + counter.incrementAndGet());
        Map<String,String> clientMap = Maps.newHashMap();
        clientMap.put("client_id", MeicanAPIConstant.CLIENT_ID);
        clientMap.put("client_secret",MeicanAPIConstant.CLIENT_SECRET);
        clientMap.put("grant_type","password");
        clientMap.put("username",Args.USERNAME);
        clientMap.put("password",Args.PASSWORD);

        // synchronous
        Call<Token> call = oauth2Service.oauth(clientMap);
        retrofit2.Response response =  call.execute();
        if(response.isSuccessful()){
            Token token = (Token) response.body();
            if(StringUtils.isEmpty(token.getError()) && StringUtils.isNotEmpty(token.getAccess_token())){
                String authorization = new StringBuffer().append("bearer ").append(token.getAccess_token()).toString();
                LOG.info(authorization);
                 dinner(authorization,Args.DISHID_DEFAULT);
            }else{
                LOG.info("error:"+token);
            }
        }else{
            try {
                LOG.info(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dinner(String authorization ,Integer dishId) throws IOException {
        JSONArray orderArray = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("dishId",Args.DISHID_DEFAULT);
        item.put("count",1);
        orderArray.add(item);
        Map<String,String> clientMap = Maps.newHashMap();
        clientMap.put("client_id",MeicanAPIConstant.CLIENT_ID);
        clientMap.put("client_secret",MeicanAPIConstant.CLIENT_SECRET);
        clientMap.put("tabUniqueId",Args.TABUNIQUEID_DEFAULT);
        clientMap.put("corpAddressUniqueId",Args.CORPADDRESSUNIQUEID_DEFAULT);
        clientMap.put("targetTime",getTargetTimeStr());
//        clientMap.put("grant_type","client_credentials");
        clientMap.put("order",orderArray.toJSONString());

        // synchronous
        Call<Status> call = orderService.order(authorization,clientMap);
        retrofit2.Response response = call.execute();
        if(response.isSuccessful()){
            Status status = (Status) response.body();
            LOG.debug(response.body().toString());
            if(StringUtils.isEmpty(status.getError())){
                mailWorker.success("成功！" + new Return(response.code(), response.message()));
            }else{
                mailService.send("i@qiancheng.me","faiure",status.toString());
            }
        }else{
//            mailService.send("i@qiancheng.me","faiure",StringEscapeUtils.unescapeJava(response.errorBody().string()));
            mailWorker.faiure(StringHelper.removeBlank(StringEscapeUtils.unescapeJava(response.errorBody().string())));
        }
    }


    private String getTargetTimeStr(){
        DateTime dt = new DateTime();
        return dt.toString(Args.TARGETTIME_FORMAT);

    }

}

