package me.qiancheng.qianworks.meicai.work;

import me.qiancheng.qianworks.meicai.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncMailWorker {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncMailWorker.class);

    @Autowired
    private MailService mailService;

    @Async
    public void success(String name) {
        String threadName = Thread.currentThread().getName();
            mailService.send("i@qiancheng.me","success",name);
            LOG.info("   " + threadName + " success " + name);
    }

    @Async
    public synchronized void faiure(String name) {
        String threadName = Thread.currentThread().getName();
            mailService.send("i@qiancheng.me",name,"faiure");
        LOG.info("   " + threadName + " faiure " + name);
    }
}