package com.hrsmartassistant.chat;

import com.hrsmartassistant.chat.dto.OllamaRequest;
import com.hrsmartassistant.chat.dto.OllamaResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OllamaService {

// we here are using restclient to actually create hhtp url to sent the model,question, stream json to ollama
// because ollama llm expects in this format .
//    private final RestClient restClient;
//    public OllamaService(RestClient.Builder builder) {
//
//        this.restClient=builder.baseUrl("http://localhost:11434").build();
//    }
//    public OllamaResponse generate(OllamaRequest request){
//        return restClient.post()
//                .uri("/api/generate")
//                .body(request)
//                .retrieve()
//                .body(OllamaResponse.class);
//    }

    private  final OllamaChatModel Chatmodel;

    public OllamaService() {
        this.Chatmodel=OllamaChatModel.builder().baseUrl("http://127.0.0.1:11434").modelName("qwen3:8b").timeout(java.time.Duration.ofSeconds(90))
                .build();

    }
    public String generate(String question){
        return Chatmodel.chat(question);
    }

/*
*
* OllamaRequest
(model + prompt + stream)
        ↓
    POST request
        ↓
http://localhost:11434/api/generate
        ↓
      Ollama
        ↓
Ollama JSON response
        ↓
OllamaResponse
* */
}
