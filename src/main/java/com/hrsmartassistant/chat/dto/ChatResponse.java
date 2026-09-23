package com.hrsmartassistant.chat.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {

    private String answer;
    private List<Source> sources;


}