package me.qiancheng.qianworks.meicai;

import me.qiancheng.qianworks.meicai.base.Context;
import me.qiancheng.qianworks.meicai.config.GenericConfig;
import me.qiancheng.qianworks.meicai.config.SchedulerConfig;
import me.qiancheng.qianworks.meicai.config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import static sun.misc.Version.println;

/**
 * Created by iamya on 6/25/2016.
 */
@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages="me.qiancheng.qianworks")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
