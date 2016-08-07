package me.qiancheng.qianworks.meicai.config;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.ServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

@Configuration
@EnableAutoConfiguration
@PropertySource({"classpath:app.properties","classpath:meican.properties","classpath:mail.properties"})
public class GenericConfig {

    private volatile static ServerConfig mailServerConfig;
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


    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

                container.addErrorPages(error401Page, error404Page, error500Page);
            }
        };
    }
}


