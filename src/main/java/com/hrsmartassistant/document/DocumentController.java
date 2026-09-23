package com.hrsmartassistant.document;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {

    private final IngestionService ingestionService;

    public DocumentController(IngestionService ingestionService){
        this.ingestionService = ingestionService;

    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        try {
            Path uploadPath = Path.of("uploads");

            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            file.transferTo(filePath);

            ingestionService.ingest(filePath);

            return ResponseEntity.ok(
                    "Document uploaded and ingested successfully: "
                            + file.getOriginalFilename()
            );

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to process document");
        }
    }

}
