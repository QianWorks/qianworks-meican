package me.qiancheng.qianworks.meicai.schedule;

import org.springframework.scheduling.annotation.Async;

//@Component
public class AsyncWorker{

    @Async
    public void work(String name) {
        String threadName = Thread.currentThread().getName(); 
        System.out.println("   " + threadName + " beginning work on " + name);
        try {
            Thread.sleep(1000 * 5); // simulates work


        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("   " + threadName + " comddspleted work on " + name);
    }
}