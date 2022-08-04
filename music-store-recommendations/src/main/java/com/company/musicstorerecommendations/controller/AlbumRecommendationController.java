package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exceptions.NotFoundException;
import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AlbumRecommendationController {
    @Autowired
    ServiceLayer service;

    @GetMapping("/albumRecommendation")
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations() {
        return service.getAllAlbumRecommendations();
    }

    @GetMapping("/albumRecommendation/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<AlbumRecommendation> getAlbumRecommendationById(@PathVariable int id) {
        if (service.getAlbumRecommendationById(id).orElse(null) == null) {
            throw new NotFoundException("Album recommendation with that ID does not exists.");
        }
        return service.getAlbumRecommendationById(id);
    }

    @PostMapping("/albumRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@Valid @RequestBody AlbumRecommendation albumRecommendation) {
        return service.createAlbumRecommendation(albumRecommendation);
    }

    @PutMapping("/albumRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@Valid @PathVariable int id, @RequestBody AlbumRecommendation albumRecommendation) {
        if (id != albumRecommendation.getAlbumRecommendationId()) {
            throw new IllegalArgumentException("Input ID does not exists or match");
        }
        service.updateAlbumRecommendation(albumRecommendation);
    }

    @DeleteMapping("/albumRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable int id) {
        if (service.getAlbumRecommendationById(id).orElse(null) == null) {
            throw new NotFoundException("Album recommendation with that ID does not exists.");
        }
        service.deleteAlbumRecommendation(id);
    }
}
