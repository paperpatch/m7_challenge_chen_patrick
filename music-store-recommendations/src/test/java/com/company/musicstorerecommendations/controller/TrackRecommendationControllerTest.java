package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {

    @MockBean
    ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<TrackRecommendation> expectedTrackRecommendationList;
    private TrackRecommendation expectedTrackRecommendation;
    private TrackRecommendation inputTrackRecommendation;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedTrackRecommendationList = Arrays.asList(
                new TrackRecommendation(1, 1, 1, true),
                new TrackRecommendation(2, 2, 2, true)
        );
        expectedTrackRecommendation = new TrackRecommendation(1, 1,1, true);
        inputTrackRecommendation = new TrackRecommendation(1, 1, 1, true);

        when(service.getAllTrackRecommendations()).thenReturn(expectedTrackRecommendationList);
        when(service.getTrackRecommendationById(1)).thenReturn(Optional.of(expectedTrackRecommendation));
        when(service.createTrackRecommendation(inputTrackRecommendation)).thenReturn(expectedTrackRecommendation);
    }

    @Test
    public void shouldReturnTrackRecommendationListAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedTrackRecommendationList);

        mockMvc.perform(get("/trackRecommendation"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTrackRecommendationByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedTrackRecommendation);

        mockMvc.perform(get("/trackRecommendation/1"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatus404WhenTrackRecommendationIdNotAvailable() throws Exception {
        mockMvc.perform(get("/trackRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateTrackRecommendationAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedTrackRecommendation);
        inputJson = mapper.writeValueAsString(inputTrackRecommendation);

        mockMvc.perform(post("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn400WithBadTrackRecommendationPostRequest() throws Exception {
        HashMap<String, Object> invalid = new HashMap();
        invalid.put("title", "nothing");
        invalid.put("trackId", "string");
        inputJson = mapper.writeValueAsString(invalid);

        mockMvc.perform(post("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateTrackRecommendationWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputTrackRecommendation);

        mockMvc.perform(put("/trackRecommendation/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422IfPutTrackRecommendationIdNotExist() throws Exception {
        inputJson = mapper.writeValueAsString(inputTrackRecommendation);

        mockMvc.perform(put("/trackRecommendation/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteTrackRecommendationWithStatus204() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404IfTrackRecommendationIsNotFound() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
