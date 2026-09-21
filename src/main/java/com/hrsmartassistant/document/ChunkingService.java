package com.hrsmartassistant.document;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChunkingService {

    // we will take the document and will split it into chunks and collect it in list ( type is textSegemt )

    public List<TextSegment> chunk(Document document){

        DocumentSplitter splitter= DocumentSplitters.recursive(500,50);
        return splitter.split(document);

    }
}
