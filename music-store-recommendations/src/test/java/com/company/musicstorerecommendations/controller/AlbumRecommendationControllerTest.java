package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {

    @MockBean
    ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<AlbumRecommendation> expectedAlbumRecommendationList;
    private AlbumRecommendation expectedAlbumRecommendation;
    private AlbumRecommendation inputAlbumRecommendation;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedAlbumRecommendationList = Arrays.asList(
                new AlbumRecommendation(1, 1, 1, true),
                new AlbumRecommendation(2, 2, 2, true)
        );
        expectedAlbumRecommendation = new AlbumRecommendation(1, 1,1, true);
        inputAlbumRecommendation = new AlbumRecommendation(1, 1, 1, true);

        when(service.getAllAlbumRecommendations()).thenReturn(expectedAlbumRecommendationList);
        when(service.getAlbumRecommendationById(1)).thenReturn(Optional.of(expectedAlbumRecommendation));
        when(service.createAlbumRecommendation(inputAlbumRecommendation)).thenReturn(expectedAlbumRecommendation);
    }

    @Test
    public void shouldReturnAlbumRecommendationListAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedAlbumRecommendationList);

        mockMvc.perform(get("/albumRecommendation"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAlbumRecommendationByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedAlbumRecommendation);

        mockMvc.perform(get("/albumRecommendation/1"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatus404WhenAlbumRecommendationIdNotAvailable() throws Exception {
        mockMvc.perform(get("/albumRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateAlbumRecommendationAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedAlbumRecommendation);
        inputJson = mapper.writeValueAsString(inputAlbumRecommendation);

        mockMvc.perform(post("/albumRecommendation")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn400WithBadAlbumRecommendationPostRequest() throws Exception {
        HashMap<String, Object> invalid = new HashMap();
        invalid.put("title", "nothing");
        invalid.put("albumId", "string");
        inputJson = mapper.writeValueAsString(invalid);

        mockMvc.perform(post("/albumRecommendation")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateAlbumRecommendationWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputAlbumRecommendation);

        mockMvc.perform(put("/albumRecommendation/1")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422IfPutAlbumRecommendationIdNotExist() throws Exception {
        inputJson = mapper.writeValueAsString(inputAlbumRecommendation);

        mockMvc.perform(put("/albumRecommendation/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteAlbumRecommendationWithStatus204() throws Exception {
        mockMvc.perform(delete("/albumRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404IfAlbumRecommendationIsNotFound() throws Exception {
        mockMvc.perform(delete("/albumRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
