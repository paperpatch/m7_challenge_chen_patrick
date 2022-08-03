package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelController.class)
public class LabelControllerTest {

    @MockBean
    private ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Label> expectedLabelList;
    private Label expectedLabel;
    private Label inputLabel;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedLabelList = Arrays.asList(
                new Label(1, "Deep Records", "fake-website.com"),
                new Label(2, "Core Music", "another-fake-website.com")
        );

        expectedLabel = new Label(1, "Deep Records", "fake-website.com");
        inputLabel = new Label(1, "Deep Records", "fake-website.com");

        when(service.getAllLabels()).thenReturn(expectedLabelList);
        when(service.getLabelById(1)).thenReturn(Optional.of(expectedLabel));
        when(service.createLabel(inputLabel)).thenReturn(expectedLabel);
    }

    @Test
    public void getAllLabelsShouldReturnAListAnd200() throws Exception {
        // Arrange
        expectedJson = mapper.writeValueAsString(expectedLabelList);

        // Act
        mockMvc.perform(get("/labels"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnLabelByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedLabel);

        mockMvc.perform(get("/labels/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturn404IfLabelNotFound() throws Exception {
        mockMvc.perform(get("/labels/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createLabelShouldReturnNewLabelAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedLabel);
        inputJson = mapper.writeValueAsString(inputLabel);

        mockMvc.perform(post("/labels")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateLabelWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputLabel);

        mockMvc.perform(put("/labels/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus422IfLabelNotFoundForPutRequest() throws Exception {
        inputJson = mapper.writeValueAsString(inputLabel);

        mockMvc.perform(put("/labels/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteLabelWithStatus204() throws Exception {
        mockMvc.perform(delete("/labels/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus404IfLabelNotFoundForDeleteRequest() throws Exception {
        mockMvc.perform(delete("/labels/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
