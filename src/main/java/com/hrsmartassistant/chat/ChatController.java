package com.hrsmartassistant.chat;

import com.hrsmartassistant.chat.dto.ChatRequest;
import com.hrsmartassistant.chat.dto.ChatResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse returnChat(@RequestBody ChatRequest chatRequest) {
        return chatService.processQuestion(chatRequest.getQuestion());
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
