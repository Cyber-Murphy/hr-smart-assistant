package com.hrsmartassistant.chat;

import com.hrsmartassistant.chat.dto.ChatResponse;
import com.hrsmartassistant.chat.dto.OllamaRequest;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final OllamaService ollamaService;
    private final ChromaService chromaService;

    public ChatService(OllamaService ollamaService, ChromaService chromaService) {

        this.ollamaService = ollamaService;
        this.chromaService = chromaService;
    }

//    public ChatResponse processQuestion(String question) {
//
//        String answer= ollamaService.generate(question);
//
//        ChatResponse chatResponse= new ChatResponse();
//        chatResponse.setAnswer(answer);
//
//        return chatResponse;
//
//    }

    public ChatResponse processQuestion(String question){
        //1st take the context;
        String context=CreateContext(question);
        // create prompt
        String prompt = """
            You are an HR assistant.

            Answer the user's question using the provided context.
            If the answer is not present in the context, say that
            the information is not available in the provided policy.

            Context:
            %s

            Question:
            %s
            """.formatted(context, question);

        String answer= ollamaService.generate(prompt);
        ChatResponse chatResponse=new ChatResponse();
        chatResponse.setAnswer(answer);
        return chatResponse;

    }

    public String CreateContext(String text){
        // we will take the
        List<EmbeddingMatch<TextSegment>> textMatches= chromaService.search(text);

        // now will do stream.map.collect
        return textMatches.stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n\n"));

    }
}
/*
* OllamaService → injected using constructor DI.
OllamaRequest → DTO containing model, prompt, stream.
setPrompt(question) → puts user's question into Ollama request.
ollamaService.generate(request) → sends request to Ollama.
OllamaResponse → receives Ollama's response.
ChatResponse → converts Ollama's response into our API response.*/