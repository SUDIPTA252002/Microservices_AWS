package com.AuthService.Config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.AuthService.EventProducer.UserInfoEvent;
import com.AuthService.Serializer.UserInfoSerializer;

@Configuration
public class KafkaProducerConfig 
{

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    

    @Bean
    public ProducerFactory<String,UserInfoEvent> producerFactory()
    {
        Map<String,Object> configProps=new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,UserInfoSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String,UserInfoEvent> kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }
}
