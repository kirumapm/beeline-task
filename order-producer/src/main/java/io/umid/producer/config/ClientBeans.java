package io.umid.producer.config;

import io.umid.producer.security.OAuth2ClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientBeans {

    @Bean
    public RestTemplate restTemplate(@Value("${client.order-consumer-uri}") String orderConsumerUri,
                                     OAuth2ClientHttpRequestInterceptor clientHttpRequestInterceptor) {
        return new RestTemplateBuilder()
                .rootUri(orderConsumerUri)
                .additionalInterceptors(clientHttpRequestInterceptor)
                .build();
    }


}
