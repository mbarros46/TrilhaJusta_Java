package com.trilhajusta.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NovaCandidaturaListener {

    private static final Logger log = LoggerFactory.getLogger(NovaCandidaturaListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(NovaCandidaturaEvent event) {
        try {
            log.info("Nova candidatura recebida: {}", event);
            // TODO: adicionar lógica real de processamento (ex: notificar, auditar, etc.)
        } catch (Exception ex) {
            log.error("Falha ao processar evento de candidatura {}", event, ex);
            // opcional: rethrow para requeue dependendo da config do container
            throw ex;
        }
    }
}