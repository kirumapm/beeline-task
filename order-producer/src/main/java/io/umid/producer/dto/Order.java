package io.umid.producer.dto;

public record Order(
        Integer id,
        String name,
        String details,
        String username
) {

}
