package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {

    @MockBean
    ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<ArtistRecommendation> expectedArtistRecommendationList;
    private ArtistRecommendation expectedArtistRecommendation;
    private ArtistRecommendation inputArtistRecommendation;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedArtistRecommendationList = Arrays.asList(
                new ArtistRecommendation(1, 1, 1, true),
                new ArtistRecommendation(2, 2, 2, true)
        );
        expectedArtistRecommendation = new ArtistRecommendation(1, 1,1, true);
        inputArtistRecommendation = new ArtistRecommendation(1, 1, 1, true);

        when(service.getAllArtistRecommendations()).thenReturn(expectedArtistRecommendationList);
        when(service.getArtistRecommendationById(1)).thenReturn(Optional.of(expectedArtistRecommendation));
        when(service.createArtistRecommendation(inputArtistRecommendation)).thenReturn(expectedArtistRecommendation);
    }

    @Test
    public void shouldReturnArtistRecommendationListAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedArtistRecommendationList);

        mockMvc.perform(get("/artistRecommendation"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnArtistRecommendationByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedArtistRecommendation);

        mockMvc.perform(get("/artistRecommendation/1"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatus404WhenArtistRecommendationIdNotAvailable() throws Exception {
        mockMvc.perform(get("/artistRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateArtistRecommendationAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedArtistRecommendation);
        inputJson = mapper.writeValueAsString(inputArtistRecommendation);

        mockMvc.perform(post("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn400WithBadArtistRecommendationPostRequest() throws Exception {
        HashMap<String, Object> invalid = new HashMap();
        invalid.put("title", "nothing");
        invalid.put("artistId", "string");
        inputJson = mapper.writeValueAsString(invalid);

        mockMvc.perform(post("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateArtistRecommendationWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputArtistRecommendation);

        mockMvc.perform(put("/artistRecommendation/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422IfPutArtistRecommendationIdNotExist() throws Exception {
        inputJson = mapper.writeValueAsString(inputArtistRecommendation);

        mockMvc.perform(put("/artistRecommendation/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteArtistRecommendationWithStatus204() throws Exception {
        mockMvc.perform(delete("/artistRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404IfArtistRecommendationIsNotFound() throws Exception {
        mockMvc.perform(delete("/artistRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
