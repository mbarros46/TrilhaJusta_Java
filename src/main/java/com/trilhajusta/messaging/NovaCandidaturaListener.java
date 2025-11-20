package com.trilhajusta.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NovaCandidaturaListener {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(NovaCandidaturaEvent event) {
        // TODO: adicionar lógica real de processamento (ex: notificar, auditar, etc.)
        System.out.println("Nova candidatura recebida: " + event);
    }
}