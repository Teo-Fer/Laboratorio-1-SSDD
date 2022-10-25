package com.banco;

import com.banco.kafka.Event;
import com.banco.kafka.Producer;
import com.banco.kafka.serdes.EventDeserializer;
import com.banco.kafka.serdes.EventSerializer;
import com.banco.model.Movement;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<Integer, Event<Movement>> producerFactoryMovement() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
                        ProducerConfig.RETRIES_CONFIG, 0,
                        ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventSerializer.class
                ));
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProperties());
    }

    @Bean
    public Map<String, Object> consumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
                ConsumerConfig.GROUP_ID_CONFIG, "banco-kafka",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false,
                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EventDeserializer.class);
    }

    @Bean
    public KafkaTemplate<Integer, Event<Movement>> kafkaTemplateMovement() {
        return new KafkaTemplate<>(producerFactoryMovement());
    }

    @Bean
    public Producer<Movement> producerMovement() {
        return new Producer<Movement>(kafkaTemplateMovement());
    }
}
