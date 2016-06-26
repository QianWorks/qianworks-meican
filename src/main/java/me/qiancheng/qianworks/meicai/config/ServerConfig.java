package me.qiancheng.qianworks.meicai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
public class ServerConfig  implements EmbeddedServletContainerCustomizer {

    /***
     * 启动端口
     */
    @Value("${http.rest.port}")
    private Integer port;

    public Integer getPort() {
        return port;
    }
   
    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(getPort());
    }
}
