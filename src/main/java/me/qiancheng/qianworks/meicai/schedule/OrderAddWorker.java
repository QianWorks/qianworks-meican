package me.qiancheng.qianworks.meicai.schedule;

import me.qiancheng.qianworks.meicai.service.Oauth2Service;
import me.qiancheng.qianworks.meicai.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderAddWorker {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAddWorker.class);

    @Autowired
    private Oauth2Service oauth2Service;
    @Autowired
    private OrderService orderService;

    @Async
    public void execute() {

            LOG.info(" ");
    }


}