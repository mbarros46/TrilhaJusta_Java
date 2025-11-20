package com.trilhajusta.services;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Service
public class AiService {

    private final Object chatClient;

    public AiService(ApplicationContext ctx) {
        Object client = null;
        try {
            Class<?> chatClientClass = Class.forName("org.springframework.ai.chat.ChatClient");
            client = ctx.getBean(chatClientClass);
        } catch (Exception ex) {
            // ChatClient not available at runtime
            client = null;
        }
        this.chatClient = client;
    }

    public String recomendarTrilhas(String perfilTexto) {
        if (this.chatClient == null) {
            throw new UnsupportedOperationException("AI provider not configured (ChatClient bean not found)");
        }
        try {
            String system = "Você é um assistente de carreira. Gere exatamente 3 trilhas de requalificação\n" +
                    "com títulos, motivo e 3 cursos sugeridos por trilha. Seja direto.";
            String user = "Perfil do usuário: " + perfilTexto + "\nResponda em português, formato markdown simples.";
            String promptText = system + "\n\n" + user;

            Class<?> promptClass = Class.forName("org.springframework.ai.chat.prompt.Prompt");
            Constructor<?> ctor = promptClass.getConstructor(String.class);
            Object prompt = ctor.newInstance(promptText);

            Method callMethod = chatClient.getClass().getMethod("call", Object.class);
            Object response = callMethod.invoke(chatClient, prompt);

            Method getResult = response.getClass().getMethod("getResult");
            Object result = getResult.invoke(response);
            Method getOutput = result.getClass().getMethod("getOutputContent");
            Object output = getOutput.invoke(result);
            return (String) output;
        } catch (RuntimeException rex) {
            throw rex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to call AI provider", ex);
        }
    }
}
