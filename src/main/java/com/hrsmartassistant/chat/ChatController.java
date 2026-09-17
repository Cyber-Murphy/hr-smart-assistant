package com.hrsmartassistant.chat;

import com.hrsmartassistant.chat.dto.ChatRequest;
import com.hrsmartassistant.chat.dto.ChatResponse;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;

    public ChatController(
            ChatService chatService,
            EmbeddingService embeddingService, ChromaService chromaService) {

        this.chatService = chatService;
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
    }
    @GetMapping("/embedding")
    public float[] testEmbedding() {

        float[] vector = embeddingService.embed(
                "Employees receive 12 days of paternity leave."
        );

        return vector;
    }

    @PostMapping
    public ChatResponse returnChat(@RequestBody ChatRequest chatRequest) {
        return chatService.processQuestion(chatRequest.getQuestion());
    }

    @GetMapping("/store")
        public String storeChunk(){
            chromaService.storeChunk("Employees receive 12 days of paternity leave.");

            return "Stored successfully";
        }


    @GetMapping("/search")
    //@RequestParam will fetch the value and will put into the String question
    public List<EmbeddingMatch<TextSegment>> search(@RequestParam String question ){

        return chromaService.search(question);

    }

}

/*
* 1. What this controller is actually doing here ?
* answer:- If the frontend sends the question:"How many days actuall leave "
* the format is json so it sends to backend
* @RestController tells the spring that this controller will handle the http requests
* @RequestMapping  is the base url so spring knows
* The controller's job is to:

Receive the HTTP request
Extract the data from it
Pass that data to the application
Return a response

For now, we're only doing steps 1–4 without any actual AI.
*
* */
