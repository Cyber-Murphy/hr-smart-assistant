# HR Smart Assistant Backend

Spring Boot backend for the HR Smart Assistant.

## Planned capabilities

The project will evolve into an AI-assisted HR application using:

- Retrieval-augmented generation (RAG) for HR-policy questions
- Embeddings and a vector store for document search
- Ollama for locally hosted large language models

These capabilities are planned only; the current codebase is still the initial
backend foundation.

## Prerequisites

- Java 21
- Maven 3.9+

## Run locally

```bash
mvn spring-boot:run
```

The service starts at `http://localhost:8080`.

## Health check

```bash
curl http://localhost:8080/api/health
```

Expected response:

```json
{"status":"UP"}
```

This baseline intentionally contains no authentication, database integration,
RAG implementation, vector store, Ollama integration, or HR business logic.
