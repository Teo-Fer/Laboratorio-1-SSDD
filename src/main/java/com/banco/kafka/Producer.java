package com.banco.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Producer<Movement> {

    @Value("${banco.server.id}")
    private String serverId;
    private final KafkaTemplate<Integer, Event<Movement>> template;

    public void logCreate(String topic, Movement movement) {
        Event event = Event.builder()
                .serverId(serverId)
                .type(EventType.CREATE)
                .movement(movement)
                .build();
        log.info("Sending event: " + event.toString());
        template.send(topic, 1, event);
    }

}
