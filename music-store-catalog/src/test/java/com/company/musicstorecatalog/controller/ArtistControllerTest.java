package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Artist;
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
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @MockBean
    private ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Artist> expectedArtistList;
    private Artist expectedArtist;
    private Artist inputArtist;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() {
        expectedArtistList = Arrays.asList(
                new Artist(1, "someone", "someone", "someone"),
                new Artist(2, "somebody", "somebody", "somebody")
        );

        expectedArtist = new Artist(1, "someone", "someone", "someone");
        inputArtist = new Artist(1, "someone", "someone", "someone");

        when(service.getAllArtists()).thenReturn(expectedArtistList);
        when(service.getArtistById(1)).thenReturn(Optional.of(expectedArtist));
        when(service.createArtist(inputArtist)).thenReturn(expectedArtist);
    }

    @Test
    public void getAllArtistsShouldReturnAListAnd200() throws Exception {
        // Arrange
        expectedJson = mapper.writeValueAsString(expectedArtistList);

        // Act
        mockMvc.perform(get("/artists"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnArtistByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedArtist);

        mockMvc.perform(get("/artists/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturn404IfArtistNotFound() throws Exception {
        mockMvc.perform(get("/artists/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createArtistShouldReturnNewArtistAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedArtist);
        inputJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(post("/artists")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateArtistWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(put("/artists/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus422IfArtistNotFoundForPutRequest() throws Exception {
        inputJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(put("/artists/12345")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteArtistWithStatus204() throws Exception {
        mockMvc.perform(delete("/artists/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus404IfArtistNotFoundForDeleteRequest() throws Exception {
        mockMvc.perform(delete("/artists/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
