package br.com.zup.payment.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import br.com.zup.payment.event.PaymentAcceptedEvent;
import br.com.zup.payment.event.PaymentRejectedEvent;

@Configuration
public class KafkaConfiguration {
    public static final String CONSUMER_GROUP = "payment-group-id";
    public static final String TOPIC_REJECTED = "rejected-payments";
    public static final String TOPIC_PAID = "paid-orders";

    private String bootstrap;

    public KafkaConfiguration(@Value(value = "${spring.kafka.bootstrap-servers}") String bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic rejectedPaymentTopic() {
        return new NewTopic(TOPIC_REJECTED, 1, (short) 1);
    }
    @Bean
    public NewTopic paidOrdersTopic() {
        return new NewTopic(TOPIC_PAID, 1, (short) 1);
    }

    @Bean
    public DefaultKafkaProducerFactory messageProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean("TEMPLATE_REJECTED")
    public KafkaTemplate<String, PaymentRejectedEvent> kafkaTemplateRejection() {
        return new KafkaTemplate<String, PaymentRejectedEvent>(messageProducerFactory());
    }
    @Bean("TEMPLATE_PAID")
    public KafkaTemplate<String, PaymentAcceptedEvent> kafkaTemplatePaid() {
        return new KafkaTemplate<String, PaymentAcceptedEvent>(messageProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
