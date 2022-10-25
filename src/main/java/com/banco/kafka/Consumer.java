package com.banco.kafka;

import com.banco.model.Movement;
import com.banco.service.MovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @Autowired
    MovementService movementService;

    @Value("${banco.server.id}")
    private String serverId;

    @KafkaListener(topics="movements", groupId = "${banco.server.id}")
    public void consume(Event event) {
        log.info("Event received at: " + serverId);
        log.info("Consuming event: " + event.toString());
         if (serverId.compareTo(event.serverId)!=0) {
             if (event.type == EventType.CREATE) {
                 movementService.create((Movement) event.movement);
                 log.info("Movement inserted by consumer");
             }
         }
    }
}
