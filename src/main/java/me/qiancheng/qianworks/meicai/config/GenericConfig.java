package me.qiancheng.qianworks.meicai.config;

import me.qiancheng.qianworks.meicai.base.MeicanInterceptors;
import okhttp3.OkHttpClient;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.ServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
@EnableAutoConfiguration
@PropertySource({"classpath:app.properties","classpath:meican.properties","classpath:mail.properties"})
public class GenericConfig {

    @Value("${qianworks.meican.mail.host}")
    private String host;

    @Value("${qianworks.meican.mail.port}")
    private Integer port;

    @Value("${qianworks.meican.mail.ssl.port}")
    private Integer SSLPort;

    @Value("${qianworks.meican.mail.from}")
    private String from;

    @Value("${qianworks.meican.mail.password}")
    private String password;

    private volatile static ServerConfig mailServerConfig;

    @Bean(name = "mailSender")
    public Mailer mailConfig(){
        if (mailServerConfig == null) {
            synchronized (GenericConfig.class) {
                if (mailServerConfig == null) {
                    mailServerConfig = new ServerConfig(host, port, from, password);
                }
            }
        }
        return new Mailer(mailServerConfig);
    }

}


