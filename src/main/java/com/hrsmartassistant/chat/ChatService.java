package com.hrsmartassistant.chat;

import com.hrsmartassistant.chat.dto.ChatResponse;
import com.hrsmartassistant.chat.dto.OllamaRequest;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final OllamaService ollamaService;

    public ChatService(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public ChatResponse processQuestion(String question) {

        OllamaRequest request = new OllamaRequest();
        request.setModel("qwen3:8b");
        request.setPrompt(question);
        request.setStream(false);

        var ollamaResponse = ollamaService.generate(request);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setAnswer(ollamaResponse.getResponse());

        return chatResponse;
    }
}
/*
* OllamaService → injected using constructor DI.
OllamaRequest → DTO containing model, prompt, stream.
setPrompt(question) → puts user's question into Ollama request.
ollamaService.generate(request) → sends request to Ollama.
OllamaResponse → receives Ollama's response.
ChatResponse → converts Ollama's response into our API response.*/