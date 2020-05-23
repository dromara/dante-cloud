package cn.herodotus.eurynome.rest.configuration;

import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * '@LoadBalanced'注解表示使用Ribbon实现客户端负载均衡
     *
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {

        log.debug("[Herodotus] |- Bean [RestTemplate Configuration] Auto Configure.");

        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());

        /**
         * 默认的 RestTemplate 有个机制是请求状态码非200 就抛出异常，会中断接下来的操作。
         * 如果不想中断对结果数据得解析，可以通过覆盖默认的 ResponseErrorHandler ，
         * 对hasError修改下，让他一直返回true，即是不检查状态码及抛异常了
         */
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };

        restTemplate.setErrorHandler(responseErrorHandler);

        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new FastJsonHttpMessageConverter());

        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //读取超时5秒,默认无限限制,单位：毫秒
        factory.setReadTimeout(applicationProperties.getRestTemplate().getReadTimeout());
        //连接超时10秒，默认无限制，单位：毫秒
        factory.setConnectTimeout(applicationProperties.getRestTemplate().getConnectTimeout());
        return factory;
    }
}
