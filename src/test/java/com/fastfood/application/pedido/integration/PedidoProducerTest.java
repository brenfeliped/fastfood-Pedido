package com.fastfood.application.pedido.integration;

import com.fastfood.application.pedido.integration.dto.PedidoCriadoEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private PedidoProducer pedidoProducer;
    private final String topic = "pedido-criado-topic";

    @BeforeEach
    void setUp() {
        pedidoProducer = new PedidoProducer(kafkaTemplate);
        ReflectionTestUtils.setField(pedidoProducer, "pedidoTopic", topic);
    }

    @Test
    void deveEnviarEventoPedidoCriado() {
        PedidoCriadoEvent event = PedidoCriadoEvent.builder()
                .pedidoId(UUID.randomUUID())
                .valor(BigDecimal.TEN)
                .build();

        pedidoProducer.enviarPedidoCriado(event);

        verify(kafkaTemplate).send(eq(topic), eq(event));
    }
}
