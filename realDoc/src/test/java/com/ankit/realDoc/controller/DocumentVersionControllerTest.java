package com.ankit.realDoc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ankit.realDoc.entity.documentVersion;
import com.ankit.realDoc.service.DocumentVersionService;
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

@WebMvcTest(DocumentVersionController.class)
public class DocumentVersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentVersionService documentVersionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new DocumentVersionController()).build();
    }

    @Test
    public void testGetVersionsByDocument() throws Exception {
        documentVersion docVersion = new documentVersion();
        

        when(documentVersionService.getVersionsByDocument(1L)).thenReturn(Collections.singletonList(docVersion));

        mockMvc.perform(get("/document-versions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].docversionId").value(1));
    }

    @Test
    public void testCreateVersion() throws Exception {
        documentVersion docVersion = new documentVersion();
        

        when(documentVersionService.createVersion(1L, "content", 1L)).thenReturn(docVersion);

        mockMvc.perform(post("/document-versions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"doc\":{\"docId\":1},\"content\":\"content\",\"editedBy\":{\"userId\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docversionId").value(1));
    }

    @Test
    public void testDeleteVersion() throws Exception {
        mockMvc.perform(delete("/document-versions/1"))
                .andExpect(status().isNoContent());
    }
}
