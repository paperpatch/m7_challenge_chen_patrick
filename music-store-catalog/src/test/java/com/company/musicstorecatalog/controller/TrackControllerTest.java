package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Track;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @MockBean
    private ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Track> expectedTrackList;
    private Track expectedTrack;
    private Track inputTrack;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedTrackList = Arrays.asList(
                new Track(1, 1, "title1", 120),
                new Track(2, 2, "title2", 90)
        );

        expectedTrack = new Track(1, 1, "title1", 120);
        inputTrack = new Track(1, 1, "title1", 120);

        when(service.getAllTracks()).thenReturn(expectedTrackList);
        when(service.getTrackById(1)).thenReturn(Optional.of(expectedTrack));
        when(service.createTrack(inputTrack)).thenReturn(expectedTrack);
    }

    @Test
    public void getAllTracksShouldReturnAListAnd200() throws Exception {
        // Arrange
        expectedJson = mapper.writeValueAsString(expectedTrackList);

        // Act
        mockMvc.perform(get("/tracks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnTrackByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedTrack);

        mockMvc.perform(get("/tracks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturn404IfTrackNotFound() throws Exception {
        mockMvc.perform(get("/tracks/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTrackShouldReturnNewTrackAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedTrack);
        inputJson = mapper.writeValueAsString(inputTrack);

        mockMvc.perform(post("/tracks")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateTrackWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputTrack);

        mockMvc.perform(put("/tracks/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus422IfTrackNotFoundForPutRequest() throws Exception {
        inputJson = mapper.writeValueAsString(inputTrack);

        mockMvc.perform(put("/tracks/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteTrackWithStatus204() throws Exception {
        mockMvc.perform(delete("/tracks/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus404IfTrackNotFoundForDeleteRequest() throws Exception {
        mockMvc.perform(delete("/tracks/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
