package com.fastfood.application.pedido.integration;

import com.fastfood.application.pedido.integration.dto.PedidoCriadoEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private PedidoProducer pedidoProducer;

    private final String topic = "pedido-topic";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pedidoProducer, "pedidoTopic", topic);
    }

    @Test
    void deveEnviarPedidoCriado() {
        PedidoCriadoEvent event = new PedidoCriadoEvent(UUID.randomUUID(), BigDecimal.TEN);

        pedidoProducer.enviarPedidoCriado(event);

        verify(kafkaTemplate).send(topic, event);
    }
}
