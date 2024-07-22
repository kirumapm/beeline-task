package io.umid.producer.config;

import io.umid.producer.kafka.SuccessLoggingDelegatingProducerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaBeans {


    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {

        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(new SuccessLoggingDelegatingProducerListener());

        return kafkaTemplate;
    }

}
