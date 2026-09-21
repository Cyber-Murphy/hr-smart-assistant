package com.hrsmartassistant.chat;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.chroma.ChromaApiVersion;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChromaService {

    private final ChromaEmbeddingStore chromaEmbeddingStore;
    private final EmbeddingService embeddingService;
    public ChromaService(EmbeddingService embeddingService){

        this.embeddingService= embeddingService;

        this.chromaEmbeddingStore = ChromaEmbeddingStore.builder()
                .apiVersion(ChromaApiVersion.V2)
                .baseUrl("http://localhost:8000")
                .collectionName("hr_documents")
                .tenantName("default_tenant")
                .databaseName("default_database")
                .build();
    }


    // this stores the vector into embeddingstore but we need to store vector+originaltext+metadata
//    public void storeChunk(String text){
//        float[] vector =embeddingService.embed(text);
//
//        Embedding embedding=Embedding.from(vector);
//        chromaEmbeddingStore.add(embedding);
//    }

    // now we will store vector+original text+ metaData
    public void storeChunk(String text){

        float[] vector=embeddingService.embed(text);

        Embedding embedding=Embedding.from(vector);

        Metadata metadata=new Metadata();
        //TextSegment is a LangChain4j object that represents a piece of text that belongs to an embedding.
        TextSegment textSegment=TextSegment.from(text,metadata);

        chromaEmbeddingStore.add(embedding,textSegment);

    }

    // this method will search from th v3etor database like and will return the list of matching vectors
    // we here will be using top k

    public List<EmbeddingMatch<TextSegment>> search(String text){
        //1. convert the question into vector

        float[] vector=embeddingService.embed(text);
        //2.add those vector into embeddeding model
        Embedding embedding=Embedding.from(vector);
        // 3.now  will search that vector into the chromadb

        return chromaEmbeddingStore.search(EmbeddingSearchRequest

                .builder()
                .queryEmbedding(embedding)
                // top k most relevant chunk
                .maxResults(3)
                .build()
        ).matches();
    }


}
