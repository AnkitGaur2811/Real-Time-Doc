package com.ankit.realDoc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ankit.realDoc.entity.document;
import com.ankit.realDoc.service.DocumentService;
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

@WebMvcTest(Documentcontroller.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new Documentcontroller()).build();
    }

    @Test
    public void testGetAllDocuments() throws Exception {
        document doc = new document();
        doc.setTitle("Test Document");

        when(documentService.getAllDocuments()).thenReturn(Collections.singletonList(doc));

        mockMvc.perform(get("/documents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Document"));
    }

    @Test
    public void testGetDocumentById() throws Exception {
        document doc = new document();
        doc.setTitle("Test Document");

        when(documentService.getDocumentById(1L)).thenReturn(doc);

        mockMvc.perform(get("/documents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Document"));
    }

    @Test
    public void testCreateDocument() throws Exception {
        document doc = new document();
        doc.setTitle("Test Document");

        when(documentService.createDocument("Test Document", null)).thenReturn(doc);

        mockMvc.perform(post("/documents")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Document\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Document"));
    }

    @Test
    public void testUpdateDocument() throws Exception {
        document doc = new document();
        doc.setTitle("Updated Document");

        when(documentService.updateDocument(1L, "Updated Document")).thenReturn(doc);

        mockMvc.perform(put("/documents/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Document\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Document"));
    }

    @Test
    public void testDeleteDocument() throws Exception {
        mockMvc.perform(delete("/documents/1"))
                .andExpect(status().isNoContent());
    }
}
