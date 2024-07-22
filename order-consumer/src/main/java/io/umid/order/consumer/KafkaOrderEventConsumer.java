package io.umid.order.consumer;

import io.umid.commons.dto.CreateOrderDTO;
import io.umid.commons.dto.UpdateOrderDTO;
import io.umid.order.service.OrderService;
import io.umid.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.template.default-topic}")
public class KafkaOrderEventConsumer {


    private final OrderService orderService;


    @KafkaHandler
    public void listenCreateEvents(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                   CreateOrderDTO createOrderDTO,
                                   Acknowledgment ack) {
        log.info("Received message! Key {}, message {}", key, createOrderDTO);

        orderService.createOrder(createOrderDTO, key);

        log.debug("Acking");
        ack.acknowledge();
    }


    @KafkaHandler
    public void listenUpdateEvents(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                   UpdateOrderDTO updateOrderDTO,
                                   Acknowledgment ack) {
        log.info("Received message! Key {}, message {}", key, updateOrderDTO);

        orderService.updateOrder(updateOrderDTO, key);

        log.debug("Acking");
        ack.acknowledge();
    }

    @KafkaHandler
    public void listenDeleteEvents(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                   Integer orderId,
                                   Acknowledgment ack) {
        log.info("Received message! Key {}, message {}", key, orderId);


        orderService.deleteOrder(orderId, key);

        log.debug("Acking");
        ack.acknowledge();
    }

}
