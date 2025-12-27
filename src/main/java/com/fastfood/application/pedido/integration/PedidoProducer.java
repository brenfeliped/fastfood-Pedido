package com.fastfood.application.pedido.integration;

import com.fastfood.application.pedido.integration.dto.PedidoCriadoEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.pedido}")
    private String pedidoTopic;

    public PedidoProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarPedidoCriado(PedidoCriadoEvent event) {
        kafkaTemplate.send(pedidoTopic, event);
    }
}
