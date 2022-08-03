package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @MockBean
    private ServiceLayer service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    private List<Album> expectedAlbumList;
    private Album expectedAlbum;
    private Album inputAlbum;
    private String expectedJson;
    private String inputJson;

    @Before
    public void setUp() throws Exception {
        setupAlbumServiceMock();
    }

    private void setupAlbumServiceMock() {
        expectedAlbumList = Arrays.asList(
                new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99)),
                new Album(2, "title2", 2, LocalDate.of(2025,3,5), 2, new BigDecimal(15.99))
        );

        expectedAlbum = new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99));
        inputAlbum = new Album(1, "title", 1, LocalDate.of(2022,8,3), 1, new BigDecimal(15.99));

        when(service.getAllAlbums()).thenReturn(expectedAlbumList);
        when(service.getAlbumById(1)).thenReturn(Optional.of(expectedAlbum));
        when(service.createAlbum(inputAlbum)).thenReturn(expectedAlbum);
    }

    @Test
    public void getAllAlbumsShouldReturnAListAnd200() throws Exception {
        // Arrange
        expectedJson = mapper.writeValueAsString(expectedAlbumList);

        // Act
        mockMvc.perform(get("/albums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnAlbumByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedAlbum);

        mockMvc.perform(get("/albums/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturn404IfAlbumNotFound() throws Exception {
        mockMvc.perform(get("/albums/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createAlbumShouldReturnNewAlbumAndStatus201() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedAlbum);
        inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(post("/albums")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateAlbumWithStatus204() throws Exception {
        inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(put("/albums/1")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus422IfAlbumNotFoundForPutRequest() throws Exception {
        inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(put("/albums/12345")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteAlbumWithStatus204() throws Exception {
        mockMvc.perform(delete("/albums/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatus404IfAlbumNotFoundForDeleteRequest() throws Exception {
        mockMvc.perform(delete("/albums/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}