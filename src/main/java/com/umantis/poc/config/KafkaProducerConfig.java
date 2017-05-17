package com.umantis.poc.config;

import com.umantis.poc.Producer;
import com.umantis.poc.model.BaseMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic Kafka configuration for producer.
 *
 * @author David Espinosa.
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.servers}")
    private String servers;

    @Bean
    public Map<String,Object> producerConfigs() {
        Map<String,Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, BaseMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String,BaseMessage> kafkaTemplate(){
        return new KafkaTemplate<String, BaseMessage>(producerFactory());
    }

    @Bean
    public Producer producer() {
        return new Producer();
    }
}
