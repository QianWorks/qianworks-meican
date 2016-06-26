package me.qiancheng.qianworks.meicai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * Created by iamya on 6/25/2016.
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
//    @Autowired
//    private TaskScheduler taskScheduler;

    //schedule the task dynamically
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        /*if (this.taskScheduler == null) {
            this.localExecutor = Executors.newSingleThreadScheduledExecutor();
            this.taskScheduler = new ConcurrentTaskScheduler(this.localExecutor);
        }*/
//        taskScheduler.addTriggerTask(task, trigger);
    }
}