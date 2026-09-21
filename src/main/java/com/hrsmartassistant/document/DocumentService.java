package com.hrsmartassistant.document;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.pdf.PdfFile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class DocumentService {

    // we are using lanchain4j document parser for extracting the string , the Document object is provided by langchaing

    public Document extractText(Path pdfPath){
        /*
        * Document has text and metadata , and langchain wants the pdf extract in document format
 ├── text
 └── metadata
        * */
            return FileSystemDocumentLoader
                    .loadDocument(
                            pdfPath
                            ,new ApachePdfBoxDocumentParser());



    }
}
