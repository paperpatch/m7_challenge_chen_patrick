package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.service.ServiceLayer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {

    @MockBean
    ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<LabelRecommendation> expectedLabelRecommendationList;
    private LabelRecommendation expectedLabelRecommendation;
    private LabelRecommendation inputLabelRecommendation;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedLabelRecommendationList = Arrays.asList(
                new LabelRecommendation(1, 1, 1, true),
                new LabelRecommendation(2, 2, 2, true)
        );
        expectedLabelRecommendation = new LabelRecommendation(1, 1,1, true);
        inputLabelRecommendation = new LabelRecommendation(1, 1, 1, true);

        when(service.getAllLabelRecommendations()).thenReturn(expectedLabelRecommendationList);
        when(service.getLabelRecommendationById(1)).thenReturn(Optional.of(expectedLabelRecommendation));
        when(service.createLabelRecommendation(inputLabelRecommendation)).thenReturn(expectedLabelRecommendation);
    }

    @Test
    public void shouldReturnLabelRecommendationListAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedLabelRecommendationList);

        mockMvc.perform(get("/labelRecommendation"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnLabelRecommendationByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedLabelRecommendation);

        mockMvc.perform(get("/labelRecommendation/1"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatus404WhenLabelRecommendationIdNotAvailable() throws Exception {
        mockMvc.perform(get("/labelRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateLabelRecommendationAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedLabelRecommendation);
        inputJson = mapper.writeValueAsString(inputLabelRecommendation);

        mockMvc.perform(post("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn400WithBadLabelRecommendationPostRequest() throws Exception {
        HashMap<String, Object> invalid = new HashMap();
        invalid.put("title", "nothing");
        invalid.put("labelId", "string");
        inputJson = mapper.writeValueAsString(invalid);

        mockMvc.perform(post("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateLabelRecommendationWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputLabelRecommendation);

        mockMvc.perform(put("/labelRecommendation/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422IfPutLabelRecommendationIdNotExist() throws Exception {
        inputJson = mapper.writeValueAsString(inputLabelRecommendation);

        mockMvc.perform(put("/labelRecommendation/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteLabelRecommendationWithStatus204() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404IfLabelRecommendationIsNotFound() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
