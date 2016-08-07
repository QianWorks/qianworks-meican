package me.qiancheng.qianworks.meicai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by iamya on 6/25/2016.
 */
@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages="me.qiancheng.qianworks")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
