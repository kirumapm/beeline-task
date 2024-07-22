package io.umid.producer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
public class SuccessLoggingDelegatingProducerListener implements ProducerListener<String, Object> {

    @Override
    public void onSuccess(ProducerRecord<String, Object> producerRecord, RecordMetadata recordMetadata) {
        log.info("Successfully sent record");
        log.debug("Producer record: {}", producerRecord);
        log.debug("Record metada: {}", recordMetadata);
        ProducerListener.super.onSuccess(producerRecord, recordMetadata);
    }

    @Override
    public void onError(ProducerRecord<String, Object> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        ProducerListener.super.onError(producerRecord, recordMetadata, exception);
    }
}
