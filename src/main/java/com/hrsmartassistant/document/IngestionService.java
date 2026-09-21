package com.hrsmartassistant.document;

import com.hrsmartassistant.chat.ChromaService;
import com.hrsmartassistant.chat.EmbeddingService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.pdf.PdfFile;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class IngestionService {

    private final ChunkingService chunkingService;
    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;
    private final DocumentService documentService;

    public IngestionService(ChunkingService chunkingService
            , EmbeddingService embeddingService
            , ChromaService chromaService
    , DocumentService documentService) {
        this.chunkingService=chunkingService;
        this.embeddingService=embeddingService;
        this.chromaService=chromaService;
        this.documentService=documentService;
    }

    public void ingest(Path path){
        Document document=documentService.extractText(path);
        List<TextSegment> chunks=chunkingService.chunk(document);
        for(int i=0;i<chunks.size();i++){

            TextSegment textSegment=chunks.get(i);

            chromaService.storeChunk(textSegment.text());
        }

        // now store those embedding to chroma db
    }

}
