package com.hrsmartassistant.chat;

import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private OllamaEmbeddingModel embeddingModel;

    public EmbeddingService(){

        this.embeddingModel=OllamaEmbeddingModel.builder()
                .baseUrl("http://127.0.0.1:11434")
                .modelName("nomic-embed-text")
                .build();



    }
    // Now I have to check the convert the text into vectors
    // .content() gets the object and .vector() fetches the vector inside it
    public float[] embed(String text){
        return embeddingModel.embed(text)
                .content()
                .vector();
    }
}
