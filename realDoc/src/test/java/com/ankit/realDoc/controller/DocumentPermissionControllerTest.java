package com.ankit.realDoc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ankit.realDoc.entity.documentPermission;
import com.ankit.realDoc.service.DocumentPermissionService;
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

@WebMvcTest(DocumentPermissionController.class)
public class DocumentPermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentPermissionService documentPermissionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new DocumentPermissionController()).build();
    }

    @Test
    public void testAssignPermission() throws Exception {
        documentPermission docPermission = new documentPermission();
        docPermission.setDocId(1L);
        docPermission.setUserId(1L);
        docPermission.setPermissionId(1L);

        when(documentPermissionService.assignPermission(docPermission)).thenReturn(docPermission);

        mockMvc.perform(post("/document-permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"docId\":1,\"userId\":1,\"permissionId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.docId").value(1));
    }

    @Test
    public void testGetPermissionsByDocument() throws Exception {
        documentPermission docPermission = new documentPermission();
        docPermission.setDocId(1L);

        when(documentPermissionService.getPermissionsByDocument(1L)).thenReturn(Collections.singletonList(docPermission));

        mockMvc.perform(get("/document-permissions/document/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].docId").value(1));
    }

    @Test
    public void testGetPermissionsByUser() throws Exception {
        documentPermission docPermission = new documentPermission();
        docPermission.setUserId(1L);

        when(documentPermissionService.getPermissionsByUser(1L)).thenReturn(Collections.singletonList(docPermission));

        mockMvc.perform(get("/document-permissions/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    public void testRemovePermission() throws Exception {
        mockMvc.perform(delete("/document-permissions")
                .param("documentId", "1")
                .param("userId", "1"))
                .andExpect(status().isNoContent());
    }
}
