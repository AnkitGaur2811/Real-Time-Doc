package com.ankit.realDoc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ankit.realDoc.dto.DocumentDTO;

@Controller
public class DocumentWebSocketController {

    @MessageMapping("/edit")
    @SendTo("/topic/updates")
    public DocumentDTO editDocument(DocumentDTO documentDTO) {
        // Handle document editing logic
        return documentDTO;
    }
}
