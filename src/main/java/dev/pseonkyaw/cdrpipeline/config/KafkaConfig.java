package dev.pseonkyaw.cdrpipeline.config;

import dev.pseonkyaw.cdrpipeline.domain.Cdr;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic cdrEventsTopic(@Value("${cdr.topics.events:cdr.events}") String topic) {
        return TopicBuilder.name(topic).partitions(3).replicas(1).build();
    }

    @Bean
    public ConsumerFactory<String, Cdr> cdrConsumerFactory(
            @Value("${spring.kafka.bootstrap-servers:localhost:9092}") String bootstrap,
            @Value("${spring.kafka.consumer.group-id:cdr-pipeline}") String groupId) {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "dev.pseonkyaw.cdrpipeline.domain");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Cdr.class.getName());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Cdr> cdrKafkaListenerContainerFactory(
            ConsumerFactory<String, Cdr> cdrConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Cdr> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cdrConsumerFactory);
        factory.setConcurrency(3);
        return factory;
    }
}
