package com.ankit.realDoc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ankit.realDoc.entity.document;
import com.ankit.realDoc.entity.documentVersion;
import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.service.DocumentService;
import com.ankit.realDoc.service.DocumentVersionService;
import com.ankit.realDoc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

@WebMvcTest(DocumentWebSocketController.class)
public class DocumentWebSocketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private DocumentVersionService documentVersionService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new DocumentWebSocketController()).build();
    }

    @Test
    public void testEditDocument() throws Exception {
        document doc = new document();
        doc.setDocId(1L);
        user editor = new user();
        editor.setUserId(1L);
        documentVersion docVersion = new documentVersion();
        

        when(documentService.getDocumentById(1L)).thenReturn(doc);
        when(userService.getUserById(1L)).thenReturn(editor);
        when(documentVersionService.createVersion(1L, "content", 1L)).thenReturn(docVersion);

        mockMvc.perform(post("/ws/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"doc\":{\"docId\":1},\"editor\":{\"userId\":1},\"content\":\"content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docversionId").value(1));
    }

    @Test
    public void testGetVersionsByDocument() throws Exception {
        documentVersion docVersion = new documentVersion();

        when(documentVersionService.getVersionsByDocument(1L)).thenReturn(Collections.singletonList(docVersion));

        mockMvc.perform(get("/ws/1/versions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].docversionId").value(1));
    }
}
